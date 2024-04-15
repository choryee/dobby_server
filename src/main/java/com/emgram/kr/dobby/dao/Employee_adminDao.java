package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.login.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Employee_adminDao {

    List<User> getAllUsers();
    User getUserInfo(User user);
    User getUser(String username);
    User getEmployeeNumber(String username);
    int joinUser(User user);
    int insertToken(User user);
    int deleteToken(User user);
    int insertMemo(User user);
    int updateUser(User user);
}
