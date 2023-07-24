package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.CaldavDao;
import com.emgram.kr.dobby.dto.caldav.*;
import com.emgram.kr.dobby.utils.CaldavDataConverter;
import com.emgram.kr.dobby.utils.CaldavDocument;
import com.emgram.kr.dobby.utils.CaldavNetwork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CaldavService {

    private CaldavDao caldavDao;

    private CaldavResult caldavResult;

    @Autowired
    public CaldavService(CaldavDao caldavDao){
        this.caldavDao = caldavDao;
    }

    public CaldavResult CaldavSyncRun(CaldavClient client, Date startDate){
        caldavResult = new CaldavResult();

        Date endDate = this.getEndDate();
        client.setDomainUrl(this.getDomainUrl(client));
        client.setHomeSetUrl(this.getHomeSetUrl(client));

        CaldavCalendar calendar = this.getTargetCalendar(client);
        if(calendar == null){
            caldavResult.setCalendar(new CaldavResult.CalendarStatus(CaldavResult.CalendarStatusType.CAL_NOTFOUND));
            return caldavResult;
        }
        if(this.checkCalendarNotChange(calendar)){
            caldavResult.setCalendar(new CaldavResult.CalendarStatus(CaldavResult.CalendarStatusType.CAL_MAINTAIN));
            return caldavResult;
        }

        calendar.setEventList(this.getEventTagList(client, calendar, startDate, endDate));
        this.reflectEvent(client, calendar);
        caldavDao.updateCalendarInfo(calendar);

        return caldavResult;
    }

    private Date getEndDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }

    private String getDomainUrl(CaldavClient client){
        CaldavRequestData requestData = new CaldavRequestData(CaldavRequestType.XML_REQ_PRINCIPAL);
        CaldavDocument document = CaldavNetwork.request(client.getAuth(), client.getHostname(), requestData);
        String principalUrl = document
                .find("response")
                .find("propstat")
                .find("prop")
                .find("current-user-principal")
                .find("href")
                .text();
        return client.getHostname() + principalUrl;
    }

    private String getHomeSetUrl(CaldavClient client){
        CaldavRequestData requestData = new CaldavRequestData(CaldavRequestType.XML_REQ_HOMESET);
        CaldavDocument document = CaldavNetwork.request(client.getAuth(), client.getDomainUrl(), requestData);
        String homeSetUrl = document
                .find("response")
                .find("propstat")
                .find("prop")
                .find("calendar-home-set")
                .find("href")
                .text();
        return homeSetUrl;
    }

    private CaldavCalendar getTargetCalendar(CaldavClient client){
        String homeSetUrl = client.getHostname() + client.getHomeSetUrl();
        CaldavRequestData requestData = new CaldavRequestData(CaldavRequestType.XML_REQ_CALENDARINFO);
        CaldavDocument document = CaldavNetwork.request(client.getAuth(), homeSetUrl, requestData, 1);

        List<CaldavDocument> nodeList = document.list("response");
        for(CaldavDocument node : nodeList){
            String calendarUrl = node.find("href").text();
            String calendarId = CaldavDataConverter.splitIdfromUrl(calendarUrl);

            if(client.getCalendarId().equals(calendarId)){
                String calendarName = node
                        .find("propstat")
                        .find("prop")
                        .find("displayname")
                        .text();
                String cTag = node
                        .find("propstat")
                        .find("prop")
                        .find("getctag")
                        .text();
                return new CaldavCalendar(calendarId, calendarUrl, calendarName, cTag);
            }
        }
        return null;
    }

    private boolean checkCalendarNotChange(CaldavCalendar linkedCalendar){
        CaldavCalendar calendarInfo = caldavDao.getCalendarInfo(linkedCalendar);
        if(calendarInfo != null && calendarInfo.getCTag().equals(linkedCalendar.getCTag())){
            return true;
        }
        if(calendarInfo == null){
            caldavResult.setCalendar(
                    new CaldavResult.CalendarStatus(
                            CaldavResult.CalendarStatusType.CAL_NEW
                            , linkedCalendar.getCalendarName()
                            , linkedCalendar.getCTag()
                    )
            );
            caldavDao.insertCalendarInfo(linkedCalendar);
            return false;
        }
        caldavResult.setCalendar(
                new CaldavResult.CalendarStatus(
                        CaldavResult.CalendarStatusType.CAL_UPDATE
                        , calendarInfo.getCalendarName()
                        , calendarInfo.getCTag()
                        , linkedCalendar.getCalendarName()
                        , linkedCalendar.getCTag()
                )
        );
        return false;
    }

    private List<CaldavEvent> getEventTagList(CaldavClient client, CaldavCalendar calendar, Date startDate, Date endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T000000Z'");
        CaldavRequestData requestData = new CaldavRequestData(
                CaldavRequestType.XML_REQ_CALENDARDATEFILTER
                , sdf.format(startDate)
                , sdf.format(endDate)
        );

        String calendarUrl = client.getHostname() + calendar.getCalendarUrl();
        CaldavDocument document = CaldavNetwork.request(
                client.getAuth()
                , calendarUrl
                , requestData
                , 1
                , "REPORT"
        );

        List<CaldavEvent> result = new ArrayList<>();
        List<CaldavDocument> nodeList = document.list("response");
        for(CaldavDocument node : nodeList){
            String eventUrl = node.find("href").text();
            if(eventUrl.equals(calendar.getCalendarUrl()))
                continue;

            String eTag = node.find("propstat")
                    .find("prop")
                    .find("getetag")
                    .text();
            result.add(new CaldavEvent(eventUrl, eTag));
        }

        return result;
    }

    private void reflectEvent(CaldavClient client, CaldavCalendar calendar){
        CaldavResult.EventStatus eventStatus = new CaldavResult.EventStatus();
        List<CaldavEvent> oldEventList = caldavDao.getAllEventTagList();

        // 삭제 대상 : 기존에 있는데 신규에 없는거 , delete_dt 추가
        List<CaldavEvent> deleteEventList = oldEventList.stream()
                .filter(o->calendar.getEventList().stream().noneMatch(n->n.getEventId().equals(o.getEventId())))
                .collect(Collectors.toList());
        if(deleteEventList.size() > 0) {
            eventStatus.setDelete(caldavDao.deleteEvent(deleteEventList));
        }

        // 수정 대상 : 기존과 신규의 etag가 다른거 , 상세정보 조회 후 업데이트
        List<CaldavEvent> updateEventList = calendar.getEventList().stream()
                .filter(n->oldEventList.stream().anyMatch(o->
                        o.getEventId().equals(n.getEventId())
                                && !o.getETag().equals(n.getETag())
                ))
                .collect(Collectors.toList());
        updateEventList = this.getCalendarData(client, calendar.getCalendarUrl(), updateEventList, 40);

        // 추가 대상 : 기존에 없는데 신규에 있는거 , 상세정보 조회 후 인서트
        List<CaldavEvent> insertEventList = calendar.getEventList().stream()
                .filter(n->oldEventList.stream().noneMatch(o->o.getEventId().equals(n.getEventId())))
                .collect(Collectors.toList());
        insertEventList = this.getCalendarData(client, calendar.getCalendarUrl(), insertEventList, 40);

        if(updateEventList.size() > 0){
            eventStatus.setUpdate(updateEventList.size());
            calendar.setEventList(updateEventList);
            caldavDao.updateEvent(calendar);
        }

        if(insertEventList.size() > 0){
            eventStatus.setUpdate(insertEventList.size());
            calendar.setEventList(insertEventList);
            caldavDao.insertEvent(calendar);
        }
        caldavResult.setEvent(eventStatus);
    }

    private List<CaldavEvent> getCalendarData(CaldavClient client, String _calendarUrl,  List<CaldavEvent> eventList, int splitRange){
        List<CaldavEvent> resultList = new ArrayList<>();
        for(List<CaldavEvent> partitionList : CaldavDataConverter.partition(eventList, splitRange)){
            String requestParam = partitionList.stream()
                    .map(event->String.format("<D:href xmlns:D=\"DAV:\">%s</D:href>",event.getEventUrl()))
                    .collect(Collectors.joining());

            String calendarUrl = client.getHostname() + _calendarUrl;
            CaldavRequestData requestData = new CaldavRequestData(CaldavRequestType.XML_REQ_CALENDARDATA, requestParam);
            CaldavDocument document = CaldavNetwork.request(
                    client.getAuth()
                    , calendarUrl
                    , requestData
                    , 1
                    , "REPORT");

            List<CaldavDocument> nodeList = document.list("response");
            for(CaldavDocument node : nodeList){
                String eventUrl = node.find("href").text();
                if(eventUrl.equals(_calendarUrl))
                    continue;
                String eTag = node.find("propstat")
                        .find("prop")
                        .find("getetag")
                        .text();
                String eventData = node.find("propstat")
                        .find("prop")
                        .find("calendar-data")
                        .text();
                resultList.add(new CaldavEvent(eventUrl, eTag, eventData));
            }
        }
        return resultList;
    }
}
