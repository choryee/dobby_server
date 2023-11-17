package com.emgram.kr.dobby.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.dashboard.HolidayDashBoardDTO;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest //MockBean 정상동작 X - dao의 경우 MockBean설정시 실객체 주입
public class DashBoardServiceTest {

    @InjectMocks
    private DashBoardService dashBoardService;

    @Mock
    private DayoffDao dayoffDao;

    @Mock
    private HolidayService holidayService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private HolidayWorkService holidayWorkService;

    @Test
    @DisplayName("총 사원 남은 연차 조회가 정상적으로 이루어 져야 한다.")
    public void countRemainingDayoffTest() {
        //given
        double expectTotalDayUse = 3;
        double expectTotalDayoffRemain = 15 - expectTotalDayUse;
        int year = 2015;
        Employee employee1 = new Employee("", "M005", "매니저", 1, "매니저2", Date.valueOf("2012-05-05"));
        Employee employee2 = new Employee("", "M004", "매니저", 1, "매니저1", Date.valueOf("2012-05-05"));

        List<VerifyHolidayDto> holidayDtos = new ArrayList<>();
        holidayDtos.add(
            new VerifyHolidayDto("새해", LocalDate.parse("2015-01-01"), true, "1", false));
        holidayDtos.add(
            new VerifyHolidayDto("어린이날", LocalDate.parse("2015-05-05"), true, "1", false));

        List<DashBoardDayoffDTO> dayoffItems = Arrays.asList(
            new DashBoardDayoffDTO(employee1.getEmployeeNo(), Date.valueOf("2012-05-05"),
                "매니저", "1001", Date.valueOf("2015-05-05"), "연차", 1, Date.valueOf("2012-05-05"),employee1.getName()),
            new DashBoardDayoffDTO(employee1.getEmployeeNo(), Date.valueOf("2012-05-05"),
                "매니저", "1001", Date.valueOf("2015-05-06"), "연차", 1, Date.valueOf("2012-05-05"), employee1.getName()),
            new DashBoardDayoffDTO(employee2.getEmployeeNo(), Date.valueOf("2012-05-05"),
                "매니저", "1001", Date.valueOf("2015-05-07"), "연차", 1, Date.valueOf("2012-05-05"), employee2.getName()),
            new DashBoardDayoffDTO(employee2.getEmployeeNo(), Date.valueOf("2012-05-05"),
                "매니저", "1001", Date.valueOf("2015-05-08"), "연차", 1, Date.valueOf("2012-05-05"), employee2.getName())
        );

        List<Employee> employees = Arrays.asList(
            employee1,
            employee2
        );

        given(holidayService.getHolidays(any(LocalDate.class), any(LocalDate.class))).willReturn(
            holidayDtos);
        given(dayoffDao.findAllDayOffByYear(any(), any()))
            .willReturn(dayoffItems);
        given(employeeService.getAllEmployeeList()).willReturn(employees);
        given(employeeService.calculateTotalVacation(employee1, year)).willReturn(10.0);
        given(employeeService.calculateTotalVacation(employee2, year)).willReturn(5.0);
        given(holidayWorkService.getMuchHolidayWorker(year)).willReturn(new HolidayDashBoardDTO("M004", 3));
        given(holidayWorkService.countAllHolidayWorkByYear(2015)).willReturn(20);

        //when
        Map<String, Object> result = dashBoardService.getDashBoardInfo(year);

        //then
        Assertions.assertEquals(expectTotalDayoffRemain, result.get("totalRemainingDayoffCount"));
        Assertions.assertEquals(expectTotalDayUse, result.get("totalUseDayoffCount"));
    }
}
