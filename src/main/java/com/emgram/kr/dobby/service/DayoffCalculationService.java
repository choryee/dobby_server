package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoff;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DayoffCalculationService {

    private final EmployeeService employeeService;
    private final DayoffService dayoffService;
    private final HolidayService holidayService;

    public DayoffResult getDayoffResult(String employeeNo, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);// 추후 searchCondition 에서 받아 와야됨
        LocalDate endDate = LocalDate.of(year, 12, 31);// 추후 searchCondition 에서 받아 와야됨

        DayoffResult employee = employeeService.totalVacation(employeeNo, year);

        double totalDayoff = employee.getTotalDayoff();

        double usedDayoff = getUsedDayoff(
                dayoffService.getUsedVacation(employeeNo, year), // 사용한 연차 List<DayoffVacation>
                holidayService.getHolidays(startDate, endDate) // 공휴일 List<VerifyHolidayDto>
                .stream()
                .filter(dto -> !dto.isWeekend())
                .collect(Collectors.toList()));

        final double leftDayOff = totalDayoff - usedDayoff;

        return DayoffResult.buildDayoffResult(employee,totalDayoff,leftDayOff,usedDayoff);
    }

    public List<EmployeeDayoff> getDayoffDetails(String employeeNo,int year){
        LocalDate holidayStartDate  = LocalDate.of(year, 1, 1);// 추후 searchCondition 에서 받아 와야됨
        LocalDate holidayEndDate = LocalDate.of(year, 12, 31);// 추후 searchCondition 에서 받아 와야됨

        List<DayoffVacation> dayoffVacations = getEmployeeDayoff(dayoffService.getUsedVacation(employeeNo, year),
                holidayService.getHolidays(holidayStartDate, holidayEndDate));

        Employee employee = employeeService.getEmployeeInfo(employeeNo);

        List<EmployeeDayoff> employeeDayoffs = new ArrayList<>();

        dayoffVacations.sort(Comparator.comparing(DayoffVacation::getDayoffDt));
        DayoffVacation previousVacation = null;
        Date startDate = null;
        double usedDays = 0.0;

        //stream 으로도 처리할 수 있다 하는 데 잘 모르 겠음
        for (DayoffVacation vacation : dayoffVacations) {
            if (previousVacation == null) {
                startDate = vacation.getDayoffDt();
                usedDays = Double.parseDouble(vacation.getCodeVal());
                previousVacation = vacation;
                continue;
            }

            ///날짜와 CodeVal 체크
            if (!isNextDay(previousVacation.getDayoffDt(), vacation.getDayoffDt())
                    || !previousVacation.getCodeVal().equals(vacation.getCodeVal())) {

                employeeDayoffs.add(EmployeeDayoff.buildEmployeeDayoff(
                        employee, previousVacation, startDate, previousVacation.getDayoffDt(), usedDays));

                startDate = vacation.getDayoffDt();
                usedDays = 0.0;
            }

            usedDays += Double.parseDouble(vacation.getCodeVal());
            previousVacation = vacation;
        }

        ///리스트 휴가 정보
        if (previousVacation != null)
            employeeDayoffs.add(EmployeeDayoff.buildEmployeeDayoff(
                    employee, previousVacation, startDate, previousVacation.getDayoffDt(), usedDays));

        return employeeDayoffs;
    }

    //이것도 8버전 미만은 Calendar 사용 해야 됨
    private boolean isNextDay(Date current, Date next) {
        LocalDate currentLocalDate = current.toInstant().atZone(Calendar.getInstance().getTimeZone().toZoneId()).toLocalDate();
        LocalDate nextLocalDate = next.toInstant().atZone(Calendar.getInstance().getTimeZone().toZoneId()).toLocalDate();

       return currentLocalDate.plusDays(1).isEqual(nextLocalDate);
    }


    private double getUsedDayoff(List<DayoffVacation> dayoffVacations, List<VerifyHolidayDto> holidayDtos) {
        return dayoffVacations.stream()
                .filter(dayoffVacation -> isNotHoliday(dayoffVacation, holidayDtos))
                .mapToDouble(dayoffVacation -> Double.parseDouble(dayoffVacation.getCodeVal()))
                .sum();
    }

    private List<DayoffVacation> getEmployeeDayoff(List<DayoffVacation> dayoffVacations,List<VerifyHolidayDto> holidayDtos){
        return dayoffVacations.stream()
                .filter(dayoffVacation -> isNotHoliday(dayoffVacation, holidayDtos))
                .collect(Collectors.toList());
    }

    private boolean isNotHoliday(DayoffVacation dayoffVacation, List<VerifyHolidayDto> holidayDtos) {
        return holidayDtos.stream()
                .noneMatch(dto -> dto.getHoliday().equals(dayoffService.convertToLocalDate(dayoffVacation.getDayoffDt())));
    }

}

