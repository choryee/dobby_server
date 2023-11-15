package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffItem;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.utils.DateUtil;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final DayoffDao dayoffDao;

    private final HolidayService holidayService;

    private final EmployeeService employeeService;

    public Map<String, Double> countAllEmployeeDayoff(int year) {
        //기반 날짜 추출
        LocalDate startDate = DateUtil.getStartDayOfYear(year);
        LocalDate endDate = DateUtil.getEndDayOfYear(year);

        //날짜를 기반으로 공휴일 목록 호출
        List<VerifyHolidayDto> holidayDtoList = holidayService.getHolidays(startDate, endDate);

        //날짜를 기반으로 모든 연차 목록 호출
        List<DashBoardDayoffItem> dayoffItems= dayoffDao.findAllDayOffByYear(startDate, endDate);

        //주말, 공휴일, 연차, 사원인지 필터
        Stream<DashBoardDayoffItem> filteredDayoffItemStream = dayoffItems
            .stream()
            .filter(this::dayOffCheck)
            .filter(this::isEmployee)
            .filter((item) -> !DateUtil.isHoliday(item.getDayoffDt(), holidayDtoList))
            .filter((item) -> !DateUtil.isWeekend(item.getDayoffDt()));

        //사용한 총 연차 값
        double totalUseDayOffCount = filteredDayoffItemStream.map(DashBoardDayoffItem::getCodeVal)
            .reduce((double) 0, Double::sum);

        //전 사원 목록 추출
        List<Employee> employees = employeeService.getAllEmployeeList();

        List<Employee> filteredEmployees = employees.stream().filter(this::isEmployee).collect(Collectors.toList());
        double totalDayOffCount = 0;

        //전 사원 지급 연차 합계
        for (Employee employee : filteredEmployees){
            totalDayOffCount += employeeService.calculateTotalVacation(employee, year);
        }

        Map<String, Double> info = new HashMap<>();
        info.put("totalRemaining", totalDayOffCount - totalUseDayOffCount);
        info.put("totalUseDayOffCount", totalUseDayOffCount);

        return info;
    }

    private boolean dayOffCheck(DashBoardDayoffItem item) {
        int dayoffType = Integer.parseInt(item.getDayoffType());
        return dayoffType >= 1000 && dayoffType < 2000;
    }

    protected boolean isEmployee(Employee employee) {
        return employee.getRankName().equals("매니저");
    }

    protected boolean isEmployee(DashBoardDayoffItem item) {
        return item.getRankName().equals("매니저");
    }
}
