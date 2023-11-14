package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.CalendarDao;
import com.emgram.kr.dobby.dto.calendar.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final CalendarDao calendarDao;

    public List<Calendar> getList() {
        return calendarDao.getList();
    }
}
