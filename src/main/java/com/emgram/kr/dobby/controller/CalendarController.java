package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.calendar.Calendar;
import com.emgram.kr.dobby.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("")
    @CrossOrigin
    public ResponseEntity<List<Calendar>> getCalendar() {

        List<Calendar> list = calendarService.getList();

        return ResponseEntity.ok().body(list);
    }
}
