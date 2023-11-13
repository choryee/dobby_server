package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoff;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/dayoff")
public class DayOffController {

    private final DayoffCalculationService dayoffCalculationService;

    @GetMapping("/employee")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<DayoffResult> dayoffUse(@RequestParam String employeeNo,
                                                  @RequestParam int year){
        return new CommonResponse<>(dayoffCalculationService.getDayoffResult(employeeNo,year));
    }

    @GetMapping("/employee/remaining")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<EmployeeDayoff>> dayoffRemaining(@RequestParam String employeeNo,
                                                                @RequestParam int year){
        return new CommonResponse<>(dayoffCalculationService.getDayoffDetails(employeeNo,year));
    }


}
