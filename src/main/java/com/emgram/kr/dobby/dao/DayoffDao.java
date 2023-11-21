package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.dayoff.DayoffDefault;
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

    List<DashBoardDayoffDTO> findAllDayOffByYear(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<DayoffDefault> findAllDefaultDayoff();

    int insertDefaultDayoff(List<DayoffDefault> dayoffDefaults);

    int updateDefaultDayoff(List<DayoffDefault> dayoffDefaults);

    DayoffDefault getBaseDayoff(int year);
}
