package com.emgram.kr.dobby.dto.caldav;

import com.emgram.kr.dobby.utils.CaldavDataConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Getter
public class CaldavEvent {
    private String eventUrl;
    private String eventId;
    private String eTag;

    private String eventName;
    private Date startTime;
    private Date endTime;
    private String creator;
    private String updator;
    private Date createTime;
    private Date updateTime;

    public CaldavEvent() {}

    public CaldavEvent(String eventUrl, String eTag){
        this.eventUrl = eventUrl;
        this.eventId = CaldavDataConverter.splitIdfromUrl(eventUrl);
        this.eTag = eTag;
    }

    public CaldavEvent(String eventUrl, String eTag, String eventData){
        this.eventUrl = eventUrl;
        this.eventId = CaldavDataConverter.splitIdfromUrl(eventUrl);
        this.eTag = eTag;
        try{
            this.setCaldavEventData(eventData);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    void setCaldavEventData(String eventDataStr) throws ParseException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMdd'T'hhmmss");
        SimpleDateFormat sdfDateTimeZ = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");

        Map eventMap = (Map) CaldavDataConverter.eventDataToHash(eventDataStr).get("vcalendar");
        CaldavEventData eventData = mapper.convertValue(eventMap, CaldavEventData.class);
        CaldavEventData.Vevent vevent = eventData.getVevent();

        this.eventName = vevent.getSummary();
        this.creator = String.format("%s:%s"
                , vevent.getXNaverRegisterer().getCn()
                , vevent.getXNaverRegisterer().getXWorksmobileWid());
        this.updator = String.format("%s:%s"
                , vevent.getXNaverLastModifier().getCn()
                , vevent.getXNaverLastModifier().getXWorksmobileWid());


        CaldavEventData.Dtstart dtstart = vevent.getDtstart();
        CaldavEventData.Dtend dtend = vevent.getDtend();

        if(dtstart.getTzid() == null && dtend.getTzid() == null){
            this.startTime = sdfDate.parse(dtstart.getVal());
            this.endTime = sdfDate.parse(dtend.getVal());
            this.endTime.setTime(this.endTime.getTime() - (24*60*60*1000));
        }else{
            this.startTime = sdfDateTime.parse(dtstart.getVal());
            this.endTime = sdfDateTime.parse(dtend == null ? dtstart.getVal() : dtend.getVal());
        }

        this.createTime = sdfDateTimeZ.parse(vevent.getCreated());
        this.updateTime = sdfDateTimeZ.parse(vevent.getLastModified());
    }
}
