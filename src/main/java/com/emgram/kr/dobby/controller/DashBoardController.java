package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.service.DashBoardService;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/info")
    public CommonResponse<Map<String, Double>> getDashBoardInfo(@RequestParam(value = "year", required = false) Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        return new CommonResponse(dashBoardService.getDashBoardInfo(year));
    }
}
