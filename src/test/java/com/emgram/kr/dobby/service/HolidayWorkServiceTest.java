package com.emgram.kr.dobby.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWork;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWorkDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class HolidayWorkServiceTest {

    @Autowired
    private HolidayWorkService holidayWorkService;

    @BeforeEach
    public void dataSetup() {
        holidayWorkService.saveWorkDays(
            new HolidayWork(null, "M002", LocalDate.of(2015, 1, 1), "특수"));
        holidayWorkService.saveWorkDays(
            new HolidayWork(null, "M002", LocalDate.of(2015, 1, 2), "특수2"));
        holidayWorkService.saveWorkDays(
            new HolidayWork(null, "M002", LocalDate.of(2015, 1, 3), "특수3"));
        holidayWorkService.saveWorkDays(
            new HolidayWork(null, "M002", LocalDate.of(2015, 1, 3), "특수3"));
        holidayWorkService.saveWorkDays(
            new HolidayWork(null, "M001", LocalDate.of(2015, 1, 5), "특수5"));
    }

    @Test
    @DisplayName("휴일 목록을 정상적으로 가져올 수 있어야한다.")
    @Transactional
    public void getWorksDayTest() {
        //given
        SearchCondition searchCondition = new SearchCondition(0, 10, 2015, 1, "");
        //when
        PageInfo<HolidayWorkDto> list = holidayWorkService.getWorkDays(searchCondition);
        //then
        assertThat(list.getContent().size(), greaterThanOrEqualTo(4));
    }

    @Test
    @DisplayName("사번 년도 별로 휴일 출근을 조회할 수 있어야한다")
    @Transactional
    public void getWorksDayCountByEmployee() {
        //given
        int year = 2015;
        String employeeNum = "M001";
        //when
        Long count = holidayWorkService.countWorkDayByYearAndEmployeeNo(employeeNum, year);
        //then
        assertEquals(count, 1);
    }
}
