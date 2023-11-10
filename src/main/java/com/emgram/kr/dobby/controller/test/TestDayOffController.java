package com.emgram.kr.dobby.controller.test;

import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.dayoff.DayoffVacation;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import com.emgram.kr.dobby.service.DayoffService;
import com.emgram.kr.dobby.service.EmployeeService;
import com.emgram.kr.dobby.service.HolidayService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
public class TestDayOffController {

   private final DayoffCalculationService dayoffCalculationService;
   private final DayoffService dayoffService;
   private final HolidayService holidayService;
    @GetMapping("/{empolyeeNo}")
    public List<DayoffVacation> getdata(@PathVariable String empolyeeNo){
        return dayoffService.getUsed(empolyeeNo);
    }

    @GetMapping("/test")
    public DayoffResult getdata2(@RequestParam String empolyeeNo,
                                 @RequestParam int year){
        return dayoffCalculationService.getDayoffResult(empolyeeNo,year);
    }
}

