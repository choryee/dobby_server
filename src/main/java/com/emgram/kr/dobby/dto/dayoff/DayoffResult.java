package com.emgram.kr.dobby.dto.dayoff;

import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.service.DayoffService;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Getter
//@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DayoffResult extends Employee{

    private DayoffService dayoffService;

    private double totalDayoff;
    private double leftDayOff;
    private double usedDayoff;
    private  List<DayoffVacation> dayoffVacations;

    public DayoffResult(DayoffService dayoffService) {
        this.dayoffService = dayoffService;
    }

    public static DayoffResult buildDayoffResult( Employee employee, double totalDayoff, double leftDayOff, double usedDayoff, int year) {
       // List<DayoffVacation> dayoffVacation = dayoffService.getUsedDayoff(employee.getEmployeeNo(), year);

        // DayoffService dayoffService = new DayoffService(dayoffDao);
        //List<DayoffVacation> dayoffVacation1 = makeDayOffServiceResult(employee.getEmployeeNo(), year)
        //List<DayoffVacation> dayoffVacation = dayoffService.getUsedDayoff("M073", 2023);

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

    public  DayoffResult buildDayoffResult1( Employee employee, double totalDayoff, double leftDayOff, double usedDayoff, int year) {

        List<DayoffVacation> dayoffVacation = dayoffService.getUsedDayoff(employee.getEmployeeNo(), year);

        // DayoffService dayoffService = new DayoffService(dayoffDao);
        //List<DayoffVacation> dayoffVacation1 = makeDayOffServiceResult(employee.getEmployeeNo(), year)
        //List<DayoffVacation> dayoffVacation = dayoffService.getUsedDayoff("M073", 2023);

        return DayoffResult.builder()
                .employeeNo(employee.getEmployeeNo())
                .rankName(employee.getRankName())
                .name(employee.getName())
                .departmentNo(employee.getDepartmentNo())
                .joiningDt(employee.getJoiningDt())
                .totalDayoff(totalDayoff)
                .leftDayOff(leftDayOff)
                .usedDayoff(usedDayoff)

                .dayoffVacations(dayoffVacation)
                .build();
    }

//    public static List<DayoffVacation>  makeDayOffServiceResult(String employeeId, int year){
//        List<DayoffVacation> dayoffVacation = dayoffService.getUsedDayoff(employeeId, year);
//        return dayoffService.getUsedDayoff(employeeId, year);
//    }

//    public static DayoffResult buildTotalDayoffResult(DayoffVacation dayoffVacation,Employee employee, double totalDayoff) {
//        return buildDayoffResult(dayoffVacation, employee, totalDayoff, 0, 0);
//    }


}
