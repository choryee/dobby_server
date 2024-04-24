package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.calendar.Calendar;
import com.emgram.kr.dobby.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("")
    @CrossOrigin
    public ResponseEntity< List<Calendar> > getCalendar() throws ParseException {

        List<Calendar> list = calendarService.getList();
        System.out.println("List<Calendar> list.size() >> "+ list.size()); //1161

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2024-03-01");

//        List<Calendar> listName = list.stream()
//                .filter(a -> a.getStartDt().after(date)))
//                .collect(Collectors.toList() );
//
//        for(Calendar calendar : listName){
//            System.out.println("calendar>> "+ calendar);
//        }

        return ResponseEntity.ok().body(list);
    }
}
