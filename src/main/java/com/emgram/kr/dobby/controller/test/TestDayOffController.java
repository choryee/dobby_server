package com.emgram.kr.dobby.controller.test;

import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import com.emgram.kr.dobby.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
public class TestDayOffController {

   private final DayoffCalculationService dayoffCalculationService;

    @GetMapping("/tes/{empolyeeNo}")
    public DayoffResult getdata2(@PathVariable String empolyeeNo){
        return dayoffCalculationService.getDayoffResult(empolyeeNo);
    }
}

