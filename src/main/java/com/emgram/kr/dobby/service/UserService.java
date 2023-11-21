package com.emgram.kr.dobby.service;


import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Employee_adminDao Employee_adminDao;

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

        int result = Employee_adminDao.joinUser(user);
        System.out.println("result>>> "+ result);

    }

    public String  login(User user){

      String name = user.getName();
      String token = null;
      token = JwtTokenUtil.createToken(name); //
      user.setName(name);
      user.setToken(token);
        return token;
    }

    public List<User> getAllUsers(){
       return Employee_adminDao.getAllUsers();
    }

    public User getUser(String username){
       return Employee_adminDao.getUser(username);
    }

    public int update(User user){

        String encPassword = encoder.encode(user.getPassword());
        User user1 = new User();
        user1.setName(user.getName());
        user1.setPassword(encPassword);

        int result=0;
        if(user.getPassword() !=null){
            result = Employee_adminDao.updateUser(user1);
        }
        if(result == 1){
            return 1;
        }
        return 0;
    }

    public void insertMemo(User user){
        Employee_adminDao.insertMemo(user);
    }

    public void getMemo(String userName){
        Employee_adminDao.getUser(userName);

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
