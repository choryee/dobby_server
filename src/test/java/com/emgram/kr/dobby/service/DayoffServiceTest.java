import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.service.DayoffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DayoffServiceTest {

    @InjectMocks
    private DayoffService dayoffService;

    @Mock
    private DayoffDao dayoffDao;

    @Test
    @DisplayName("주말과 dayoffType을 확인하여 분류 테스트")
    public void testGetUsedVacation() {
        // Arrange
        String employeeId = "M001";
        int year = 2023;

        List<DayoffVacation> mockDayoffVacations = new ArrayList<>();
        mockDayoffVacations.add(createDayoffVacation("2023-01-01", "1001")); // 주말
        mockDayoffVacations.add(createDayoffVacation("2023-02-15", "1002")); // 평일
        mockDayoffVacations.add(createDayoffVacation("2023-05-01", "1003")); // 평일
        mockDayoffVacations.add(createDayoffVacation("2023-12-25", "2001")); // 주말
        mockDayoffVacations.add(createDayoffVacation("2023-12-26","5000"));//5000번
        when(dayoffDao.infoDayOffEmployeeNo(employeeId)).thenReturn(mockDayoffVacations);

        List<DayoffVacation> result = dayoffService.getUsedVacation(employeeId, year);

        assertEquals(2, result.size());

        verify(dayoffDao, times(1)).infoDayOffEmployeeNo(employeeId);
    }

    private DayoffVacation createDayoffVacation(String date, String dayoffType) {
        Date dayoffDt = Date.from(LocalDate.parse(date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return new DayoffVacation("employeeNoValue", dayoffType, dayoffDt, "codeNameValue", "codeValValue");
    }
}
