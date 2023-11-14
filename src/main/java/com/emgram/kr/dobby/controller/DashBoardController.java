package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.service.DashBoardService;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/dayoff/remaing")
    public CommonResponse<Map<String, Double>> getRemaingDayoffCount(@RequestParam(value = "year", required = false) int year) {
        if (year < 2015) {
            year = LocalDate.now().getYear();
        }
        return new CommonResponse(dashBoardService.countAllEmployeeDayoff(year));
    }
}
