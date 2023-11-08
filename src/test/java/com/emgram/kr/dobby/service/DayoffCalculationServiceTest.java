import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import com.emgram.kr.dobby.service.DayoffService;
import com.emgram.kr.dobby.service.EmployeeService;
import com.emgram.kr.dobby.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    public void testGetDayoffResult() {
        // Arrange
        String employeeNo = "E001";
        int year = 2023;

        double totalDayoff = 0.0;
        double usedDayoff = 0.0;

        List<DayoffVacation> dayoffVacations = new ArrayList<>();
        dayoffVacations.add(createDayoffVacation("2023-02-15", "1001","연차","1"));
        dayoffVacations.add(createDayoffVacation("2023-05-01", "1002","오전반차","0.5"));
        dayoffVacations.add(createDayoffVacation("2023-01-01", "1002","오전반차","0.5"));
        dayoffVacations.add(createDayoffVacation("2023-11-01", "1003","오후반차","0.5"));
        dayoffVacations.add(createDayoffVacation("2023-05-01", "1001","연차","1"));
        dayoffVacations.add(createDayoffVacation("2023-06-01", "1001","연차","1"));

        List<VerifyHolidayDto> holidayDtos = new ArrayList<>();
        holidayDtos.add(new VerifyHolidayDto("새해", LocalDate.parse("2023-01-01"), true, "1", true));
        holidayDtos.add(new VerifyHolidayDto("무엇인가", LocalDate.parse("2023-05-01"), true, "1", true));



        // Act
        DayoffResult dayoffResult = dayoffCalculationService.getDayoffResult(employeeNo, year);

        // Assert
        assertEquals(totalDayoff, dayoffResult.getTotalDayoff());
        assertEquals(usedDayoff, dayoffResult.getUsedDayoff());
        assertEquals(totalDayoff - usedDayoff, dayoffResult.getLeftDayOff());
    }

    private DayoffVacation createDayoffVacation(String date, String dayoffType,String codeName,String codeVal) {
        Date dayoffDt = Date.from(LocalDate.parse(date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return new DayoffVacation("employeeNoValue", dayoffType, dayoffDt, codeName, codeVal);
    }

}
