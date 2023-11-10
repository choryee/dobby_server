package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWork;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWorkDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HolidayWorkDao {

    long countWorkDayByYearAndEmployeeNo(@Param("employeeNo") String employeeNo, @Param("startDate")
    LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<HolidayWorkDto> findAllHolidayWorkBySearchCondition(SearchCondition condition);

    int saveHolidayWork(HolidayWork holidayWork);

    int updateHolidayWork(@Param("holidayWorkId") Long holidayWorkId,
        @Param("holidayWorkDto") HolidayWorkDto holidayDto);

    int deleteHolidayWork(Long holidayWorkId);
}
