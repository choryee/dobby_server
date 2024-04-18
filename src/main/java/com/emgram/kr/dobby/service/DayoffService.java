package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import com.emgram.kr.dobby.dto.dayoff.DayoffDefault;
import com.emgram.kr.dobby.dto.dayoff.DayoffItem;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.utils.DayoffType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DayoffService {

    private DayoffDao dayoffDao;

    @Autowired
    public DayoffService(DayoffDao dayoffDao){
        this.dayoffDao = dayoffDao;
    }

    public int deleteDayoff(){
        return dayoffDao.deleteDayoffHistoryForSync();
    }

    public int insertDayoff(){
        List<CaldavEvent> eventList = dayoffDao.getEventListForSync();
        List<DayoffItem> itemList = new ArrayList<>();
        eventList.stream().forEach(event->{
            List<Date> dateList = this.getBetweenDateList(event.getStartTime(), event.getEndTime());
            itemList.addAll(dateList.stream().map(
                date-> new DayoffItem(
                    event.getEventIdx()
                    , event.getEventName()
                    , date
                    , event.getCreateTime()
                    , event.getCreator()
                    , event.getUpdateTime()
                    , event.getUpdator()
                )
            ).collect(Collectors.toList()));
        });
        if(itemList.size() > 0) {
            return dayoffDao.insertDayoffListForSync(itemList);
        }
        return 0;
    }

    ////LocalDate 사용 자바 버전에 따라 바꿔야 한다면, Calendar 로 변경 해야됨
    public List<DayoffVacation> getUsedVacation(String employeeId, int year) {

        List<DayoffVacation> list = dayoffDao.infoDayOffEmployeeNo(employeeId, year);
        for (DayoffVacation dayoffVacation : list){
            System.out.println("List<DayoffVacation> >>" +dayoffVacation.getDayoffDt());
            System.out.println("List<DayoffVacation> >>" +dayoffVacation.getDayoffType());
        }
        System.out.println("List<DayoffVacation> getUsedVacation >> "+ list); // []

       // List<DayoffVacation> list1 = dayoffDao.infoDayOffEmployeeNo(employeeId,year)
              //  .stream()
               // .filter(v -> !isWeekend(v.getDayoffDt()))
               // .filter(this::dayOffCheck)
               // .collect(Collectors.toList());


        return dayoffDao.infoDayOffEmployeeNo(employeeId,year)
                .stream()
                .filter(v -> !isWeekend(v.getDayoffDt()))
                .filter(this::dayOffCheck)
                .collect(Collectors.toList());
    }

    public List<DayoffVacation> getUsedDayoff(String employeeId, int year){
        return getUsedVacation(employeeId,year).stream().filter(this::dayOffCheck).collect(Collectors.toList());
    }

    private boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean dayOffCheck(DayoffVacation dayoffVacation) {
        int dayoffType = Integer.parseInt(dayoffVacation.getDayoffType());
        return DayoffType.DAY_OFF.inRange(dayoffType);
    }

    private List<Date> getBetweenDateList(Date start_dt, Date end_dt){
        start_dt = removeTime(start_dt);
        end_dt = removeTime(end_dt);
        List<Date> dates = new ArrayList<>();
        Date currentDate = start_dt;
        while (currentDate.compareTo(end_dt) <= 0){
            dates.add(currentDate);
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = c.getTime();
        }
        return dates;
    }

    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void setDayoffDefault(List<DayoffDefault> dayoffDefaults) {
        if(dayoffDefaults == null) throw new RuntimeException("기본 연차 정보가 들어오지 않았습니다.");

        if(dayoffDao.insertDefaultDayoff(dayoffDefaults)==0)
            throw new RuntimeException("기본 연차정보가 저장되지않았습니다.");
    }


    public void updateDefaultDayoff(List<DayoffDefault> dayoffDefaults) {
        if(dayoffDefaults == null) throw new RuntimeException("기본 연차 정보가 들어오지 않았습니다.");

        if(dayoffDao.updateDefaultDayoff(dayoffDefaults)==0)
            throw new RuntimeException("기본 연차정보가 수정되지 않았습니다.");
    }

    public List<DayoffDefault> getDayoffDefault() {
        return dayoffDao.findAllDefaultDayoff();
    }

    public DayoffDefault getBaseDayoff(int year) {
        return dayoffDao.getBaseDayoff(year);
    }
}
