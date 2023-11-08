package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.caldav.CaldavCalendar;
import com.emgram.kr.dobby.dto.caldav.CaldavEvent;
import com.emgram.kr.dobby.dto.login.UserLoginRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeDao {

    UserLoginRequest getUserInfo(String userName, String password);

}
