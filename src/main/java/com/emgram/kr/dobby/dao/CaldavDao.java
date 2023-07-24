package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.caldav.CaldavCalendar;
import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CaldavDao {
    CaldavCalendar getCalendarInfo(CaldavCalendar calendar);

    int insertCalendarInfo(CaldavCalendar calendar);

    int updateCalendarInfo(CaldavCalendar calendar);

    List<CaldavEvent> getAllEventTagList();

    int deleteEvent(List<CaldavEvent> deleteEventList);

    int updateEvent(CaldavCalendar calendar);

    int insertEvent(CaldavCalendar calendar);
}
