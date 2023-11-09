package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmployeeDao employeeDao;

    @Value("${jwt.token.secret}") // application.properties에 정의됨.
    private String key;
    private Long expireTimeMs = 1000*60*3l;


    public void join(User user){

        String name = user.getName();
        System.out.println("name>>> "+ name);
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setName(name);
        user.setPassword(encPassword);
        user.setRoles("ROLE_USER");

        int result = employeeDao.joinUser(user);
        System.out.println("result>>> "+ result);

    }

    public String  login(User user){
        System.out.println("UserService 탐... " + user);
        User userDao = employeeDao.getUserInfo(user);
        System.out.println("userDao>> "+ userDao);
        PrincipalDetail principalDetail =new PrincipalDetail(user);
        System.out.println("principalDetail.getPassword()>> "+principalDetail.getPassword());


        String name = user.getName();
        String rawPassword = user.getPassword();
        String makeEncPassoword = encoder.encode(rawPassword);
        String encPassword = encoder.encode(rawPassword);

        String token = null;
        if(principalDetail.getPassword().equals(rawPassword)){
            token = JwtTokenUtil.createToken(name, key, expireTimeMs);
        }
        System.out.println("token>> "+ token);
        return token;
    }

}
