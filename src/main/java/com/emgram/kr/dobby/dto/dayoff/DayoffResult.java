package com.emgram.kr.dobby.dto.dayoff;

import com.emgram.kr.dobby.dto.employee.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class DayoffResult extends Employee{

    private double totalDayoff;
    private double leftDayOff;
    private double usedDayoff;


    public static DayoffResult buildDayoffResult(Employee employee, double totalDayoff, double leftDayOff, double usedDayoff) {
        return DayoffResult.builder()
                .employeeNo(employee.getEmployeeNo())
                .rankName(employee.getRankName())
                .name(employee.getName())
                .departmentNo(employee.getDepartmentNo())
                .joiningDt(employee.getJoiningDt())
                .totalDayoff(totalDayoff)
                .leftDayOff(leftDayOff)
                .usedDayoff(usedDayoff)

                .build();
    }

    public static DayoffResult buildTotalDayoffResult(Employee employee, double totalDayoff) {
        return buildDayoffResult(employee, totalDayoff, 0, 0);
    }
}
