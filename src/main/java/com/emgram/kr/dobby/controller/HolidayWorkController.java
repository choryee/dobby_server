package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWork;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWorkDto;
import com.emgram.kr.dobby.service.HolidayWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/holiday/work")
public class HolidayWorkController {

    private final HolidayWorkService holidayWorkService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public CommonResponse<PageInfo<HolidayWorkDto>> getHolidayWorksList(
        @ModelAttribute SearchCondition searchCondition) {
        return new CommonResponse<>(holidayWorkService.getWorkDays(searchCondition));
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public CommonResponse saveHolidayWork(@RequestBody HolidayWork holidayWork) {
        holidayWorkService.saveWorkDays(holidayWork);
        return new CommonResponse(null);
    }

    @PutMapping("/modify/{holidayId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public CommonResponse updateHolidayWork(@PathVariable Long holidayId,
        @RequestBody HolidayWorkDto holidayWorkDto) {
        holidayWorkService.updateWorkDay(holidayId, holidayWorkDto);
        return new CommonResponse(null);
    }

    @DeleteMapping("/delete/{holidayId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public CommonResponse deleteHolidayWork(@PathVariable Long holidayId) {
        holidayWorkService.deleteWorkDay(holidayId);
        return new CommonResponse(null);
    }
}
