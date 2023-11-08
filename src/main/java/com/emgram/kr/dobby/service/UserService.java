package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.login.UserLoginRequest;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@RequiredArgsConstructor
public class UserService {

    // private final BCryptPasswordEncoder encoder;

    private EmployeeDao employeeDao;

    @Value("${jwt.token.secret}") // application.properties에 정의됨.
    private String key;
    private Long expireTimeMs = 1000*60*3l;


    public String  login(String userName, String password){
        UserLoginRequest user = employeeDao.getUserInfo(userName, password);
        String name = user.getUserName();
        String pwd = user.getPassword();

        String token= JwtTokenUtil.createToken(name, key, expireTimeMs);
        return token;
    }

}
