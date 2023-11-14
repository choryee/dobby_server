package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffItem;
import com.emgram.kr.dobby.dto.dayoff.DayoffItem;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DayoffDao {
    int deleteDayoffHistoryForSync();

    List<CaldavEvent> getEventListForSync();

    int insertDayoffListForSync(List<DayoffItem> dayoffItems);

    List<DayoffVacation> infoDayOffEmployeeNo(String employeeNo, int year);

    List<DashBoardDayoffItem> findAllDayOffByYear(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);
}
