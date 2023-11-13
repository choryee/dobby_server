package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
//        User userDao = employeeDao.getUserInfo(user);
//        System.out.println("userDao>> "+ userDao);
//        PrincipalDetail principalDetail =new PrincipalDetail(user);
//        System.out.println("principalDetail.getPassword()>> "+principalDetail.getPassword());
//
          String name = user.getName();
//        String rawPassword = user.getPassword();
//        String makeEncPassoword = encoder.encode(rawPassword);
//        String encPassword = encoder.encode(rawPassword);
//
          String token = null;
//        if(principalDetail.getPassword().equals(rawPassword)){
            token = JwtTokenUtil.createToken(name); //
            user.setName(name);
            user.setToken(token);
            employeeDao.insertToken(user);
//        }
//        System.out.println("token>> "+ token);
        return token;
    }



    public User getUser(String username){
       return employeeDao.getUser(username);
    }

    public int update(User user){
        System.out.println("UserService 탐..update >> "+ user);
        String encPassword = encoder.encode(user.getPassword());
        User user1 = new User();
        user1.setName(user.getName());
        user1.setPassword(encPassword);

        int result=0;
        if(user.getPassword() !=null){
            result = employeeDao.updateUser(user1);
        }
        if(result == 1){
            return 1;
        }
        return 0;
    }

    public String endAndModifyPassword(User user){
        String encPwd=encoder.encode(user.getPassword());
        user.setPassword(encPwd);
        return user.getPassword();
    }

    public boolean comparePwd(User user){
        boolean isMatch=false;
        if(encoder.matches(user.getPassword(), endAndModifyPassword(user))){
            isMatch=true;
        }
        return isMatch;
    }

}
