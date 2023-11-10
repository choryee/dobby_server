package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWorkDto;
import com.emgram.kr.dobby.service.HolidayWorkService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
    public CommonResponse<List<HolidayWorkDto>> getHolidayWorksList(@ModelAttribute SearchCondition searchCondition) {
        List<HolidayWorkDto> list = Arrays.asList(
            new HolidayWorkDto(1L, "M007", LocalDate.of(2015, 4, 2), "memo1"),
            new HolidayWorkDto(2L, "M007", LocalDate.of(2015, 4, 3), "memo2")
            );
        return new CommonResponse<>(list);
   }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public void saveHolidayWork(@RequestBody HolidayWorkDto holidayWorkDto) {
        holidayWorkService.saveWorkDays(holidayWorkDto);
    }

    @PutMapping("/modify/{holidayId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void updateHolidayWork(@PathVariable Long holidayId, @RequestBody HolidayWorkDto holidayWorkDto) {
        holidayWorkService.updateWorkDay(holidayId, holidayWorkDto);
    }

    @DeleteMapping("/delete/{holidayId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void deleteHolidayWork(@PathVariable Long holidayId) {
        holidayWorkService.deleteWorkDay(holidayId);
    }
}
