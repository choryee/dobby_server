package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DayoffCalculationService {

    private final EmployeeService employeeService;
    private final DayoffService dayoffService;

    public DayoffResult getDayoffResult(String employeeNo){
        double totalDayoff = employeeService.totalVacation(employeeNo);
        double usedDayoff = dayoffService.getUsedVacation(employeeNo);
        final double leftDayOff = totalDayoff - usedDayoff;
        return DayoffResult.builder()
                .totalDayoff(totalDayoff)
                .leftDayOff(leftDayOff)
                .usedDayoff(usedDayoff)
                .build();
    }

}
