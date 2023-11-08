package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HolidayDao {

    List<HolidayDto> findAllHolidayBetweenDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void insertHolidays(List<HolidayDto> holidayDtos);

    void deleteBetweenDate (@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
