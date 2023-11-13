package com.emgram.kr.dobby.dto.employee;


import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Getter
@NoArgsConstructor
public class EmployeeDayoff extends Employee {

    private String wid;
    private String dayoffType;
    private String codeName;
    private Date startDayoffDt;
    private Date endDayoffDt;
    private double usedDayoff;
    public static EmployeeDayoff buildEmployeeDayoff(Employee employee, DayoffVacation vacation, Date startDayoffDt,
                                                     Date endDayoffDt, double usedDayoff) {
        return EmployeeDayoff.builder()
                .wid(employee.getWid())
                .employeeNo(employee.getEmployeeNo())
                .rankName(employee.getRankName())
                .name(employee.getName())
                .departmentNo(employee.getDepartmentNo())
                .joiningDt(employee.getJoiningDt())
                .dayoffType(vacation.getDayoffType())
                .codeName(vacation.getCodeName())
                .startDayoffDt(startDayoffDt)
                .endDayoffDt(endDayoffDt)
                .usedDayoff(usedDayoff)
                .build();
    }
}
