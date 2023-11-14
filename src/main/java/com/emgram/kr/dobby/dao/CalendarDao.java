package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.calendar.Calendar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarDao {

    List<Calendar> getList();
}
