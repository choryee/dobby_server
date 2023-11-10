package com.emgram.kr.dobby.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emgram.kr.dobby.dao.HolidayDao;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.scheduler.CaldavSyncScheduler;
import com.emgram.kr.dobby.scheduler.DayoffSyncScheduler;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private HolidayDao holidayDao;

    @MockBean
    private DayoffSyncScheduler dayoffSyncScheduler;

    @MockBean
    private CaldavSyncScheduler caldavSyncScheduler;

    @Test
    @DisplayName("공휴일 정보를 성공적으로 조회 할 수 있어야 한다")
    public void getHolidayTest() {
        //given
        LocalDate startDate = LocalDate.of(2016, 5, 6);
        LocalDate endDate = LocalDate.of(2016, 5, 6);

        //when
        List<VerifyHolidayDto> list = holidayService.getHolidays(startDate, endDate);

        //then
        assertThat(list.size(), greaterThanOrEqualTo(1));
        assertEquals(list.get(0).getHolidayName(), "임시공휴일");
    }

    @Test
    @DisplayName("공휴일이 아닐경우 정보를 뱉지 말아야한다")
    public void notGetHolidayTest() {
        //given
        LocalDate startDate = LocalDate.of(2020, 1, 16);
        LocalDate endDate = LocalDate.of(2020, 1, 16);

        //when
        List<VerifyHolidayDto> list = holidayService.getHolidays(startDate, endDate);

        //then
        assertEquals(list.size(), 0);
    }

    @Test
    @DisplayName("공휴일 API를 성공적으로 호출해서 DB에 저장하여야 한다.")
    public void registerHolidayTest1() throws Exception {
        //given
        int year = 2015;
        LocalDate childrenDay = LocalDate.of(2015, 5, 5);

        //when
        holidayService.registerHolidays(year);

        //then
        List<HolidayDto> dtos = holidayDao.findAllHolidayBetweenDate(childrenDay, childrenDay);
        assertThat(dtos.size(), greaterThanOrEqualTo(1));
        assertEquals(dtos.get(0).getHoliday(), childrenDay);
    }
}
