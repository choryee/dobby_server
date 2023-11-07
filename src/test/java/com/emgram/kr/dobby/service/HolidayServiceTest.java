package com.emgram.kr.dobby.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emgram.kr.dobby.dao.HolidayDao;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
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
    public void registerHoliday() {
        for (int i = 2015; i <= 2025; i++) {
            try{
                holidayService.saveHolidays(i);
            }catch (Exception e){

            }
        }
    }

    @Test
    @DisplayName("공휴일 API를 성공적으로 호출해서 DB에 저장하여야 한다.")
    public void registerHolidayTest1() throws Exception {
        //given
        int year = 2015;
        LocalDate childrenDay = LocalDate.of(2015, 5, 5);

        //when
        holidayService.saveHolidays(year);

        //then
        List<HolidayDto> dtos= holidayDao.getHolidayBetweenDate(childrenDay, childrenDay);
        assertThat(dtos.size(), greaterThan(1));
        assertEquals(dtos.get(0).getHoliday(), childrenDay);
    }
}
