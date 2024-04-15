package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.dayoff.DayoffDefault;
import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoff;
import com.emgram.kr.dobby.service.DayoffCalculationService;
import com.emgram.kr.dobby.service.DayoffService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/dayoff")
public class DayOffController {

    private final DayoffCalculationService dayoffCalculationService;
    private final DayoffService dayoffService;
    @GetMapping("/employee")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<DayoffResult> dayoffUse(@RequestParam("employeeNo") String employeeNo,
                                                  @RequestParam("year") int year){
        System.out.println("employeeNo>>> "+employeeNo);
        System.out.println("year>>> "+year);

        return new CommonResponse<>(dayoffCalculationService.getDayoffResult(employeeNo,year));
    }

    @GetMapping("/employee/remaining")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<EmployeeDayoff>> dayoffRemaining(@RequestParam String employeeNo,
                                                                @RequestParam int year){
        return new CommonResponse<>(dayoffCalculationService.getDayoffDetails(employeeNo,year));
    }

    @PostMapping("/setting")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse dayoffSetting(@RequestBody List<DayoffDefault> dayoffDefaults){
        dayoffService.setDayoffDefault(dayoffDefaults);
        return new CommonResponse(null);
    }

    @PutMapping("/setting/update")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse updateDayoffSetting(@RequestBody List<DayoffDefault> dayoffDefaults){
        dayoffService.updateDefaultDayoff(dayoffDefaults);
        return new CommonResponse(null);
    }

    @GetMapping("/setting/list")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<DayoffDefault>> getDayoffDefault(){
        return new CommonResponse<>(dayoffService.getDayoffDefault());
    }
}
