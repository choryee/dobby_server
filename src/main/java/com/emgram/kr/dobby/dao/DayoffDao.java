package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import com.emgram.kr.dobby.dto.dayoff.DayoffItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DayoffDao {
    int deleteDayoffHistoryForSync();

    List<CaldavEvent> getEventListForSync();

    int insertDayoffListForSync(List<DayoffItem> dayoffItems);
}