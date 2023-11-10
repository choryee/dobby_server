package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DayoffCalculationService {

    private final EmployeeService employeeService;
    private final DayoffService dayoffService;
    private final HolidayService holidayService;

    public DayoffResult getDayoffResult(String employeeNo, int year) {
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");

        double totalDayoff = employeeService.totalVacation(employeeNo, year);
        List<DayoffVacation> dayoffVacations = dayoffService.getUsedVacation(employeeNo, year);

        List<VerifyHolidayDto> holidayDtos = holidayService.getHolidays(startDate, endDate)
                .stream()
                .filter(dto -> !dto.isWeekend())
                .collect(Collectors.toList());

        double usedDayoff = getUsedDayoff(dayoffVacations, holidayDtos);

        final double leftDayOff = totalDayoff - usedDayoff;
        return DayoffResult.builder()
                .totalDayoff(totalDayoff)
                .leftDayOff(leftDayOff)
                .usedDayoff(usedDayoff)
                .build();
    }

    private double getUsedDayoff(List<DayoffVacation> dayoffVacations, List<VerifyHolidayDto> holidayDtos) {
        return dayoffVacations.stream()
                .filter(dayoffVacation -> isNotHoliday(dayoffVacation, holidayDtos))
                .mapToDouble(this::parseCodeVal)
                .sum();
    }

    private boolean isNotHoliday(DayoffVacation dayoffVacation, List<VerifyHolidayDto> holidayDtos) {
        return holidayDtos.stream()
                .noneMatch(dto -> dto.getHoliday().equals(dayoffService.convertToLocalDate(dayoffVacation.getDayoffDt())));
    }

    private double parseCodeVal(DayoffVacation dayoffVacation) {
        return Double.parseDouble(dayoffVacation.getCodeVal());
    }
}

