import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import com.emgram.kr.dobby.service.DayoffService;
import com.emgram.kr.dobby.service.EmployeeService;
import com.emgram.kr.dobby.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DayoffCalculationServiceTest {

    @InjectMocks
    private DayoffCalculationService dayoffCalculationService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DayoffService dayoffService;

    @Mock
    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("결과 테스트")
    public void testGetDayoffResult() {
        // Arrange
        String employeeNo = "M001";
        int year = 2020;
        Employee employee = new Employee("2", employeeNo, "매니저", 1, "누군가", LocalDate.now());
        double totalDayoff =15.0;
        double usedDayoff = 2.5;

        List<DayoffVacation> dayoffVacations = new ArrayList<>();
        dayoffVacations.add(createDayoffVacation("2023-02-15", "1001","연차","1")); // 평일
        dayoffVacations.add(createDayoffVacation("2023-05-01", "1002","오전반차","0.5")); // 무엇인가
        dayoffVacations.add(createDayoffVacation("2023-01-01", "1002","오전반차","0.5")); // 새해
        dayoffVacations.add(createDayoffVacation("2023-11-01", "1003","오후반차","0.5")); // 평일
        dayoffVacations.add(createDayoffVacation("2023-05-01", "1001","연차","1")); // 무엇인가
        dayoffVacations.add(createDayoffVacation("2023-06-01", "1001","연차","1")); // 평일

        List<VerifyHolidayDto> holidayDtos = new ArrayList<>();
        holidayDtos.add(new VerifyHolidayDto("새해", LocalDate.parse("2023-01-01"), true, "1", false));
        holidayDtos.add(new VerifyHolidayDto("무엇인가", LocalDate.parse("2023-05-01"), true, "1", false));

        Mockito.when(employeeService.getEmployeeByEmployeeNo(employeeNo)).thenReturn(employee);
        Mockito.when(dayoffService.getUsedDayoff(employeeNo, year)).thenReturn(dayoffVacations);
        Mockito.when(employeeService.calculateTotalVacation(employee, year)).thenReturn(totalDayoff);
        Mockito.when(holidayService.getHolidays(any(LocalDate.class), any(LocalDate.class))).thenReturn(holidayDtos);

        // Act
        DayoffResult dayoffResult = dayoffCalculationService.getDayoffResult(employeeNo, year);

        // Assert
        assertEquals(totalDayoff, dayoffResult.getTotalDayoff());
        assertEquals(usedDayoff, dayoffResult.getUsedDayoff());
        assertEquals(totalDayoff - usedDayoff, dayoffResult.getLeftDayOff());
    }

    private DayoffVacation createDayoffVacation(String date, String dayoffType,String codeName,String codeVal) {
        LocalDate dayoffDt = LocalDate.parse(date);
        return new DayoffVacation("employeeNoValue", dayoffType, dayoffDt, codeName, codeVal);
    }

}
