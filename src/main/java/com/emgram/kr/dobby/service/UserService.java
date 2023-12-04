package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Employee_adminDao Employee_adminDao;

    private Long expireTimeMs = 1000*60*3l;


    public void join(User user){
        String name = user.getName();
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setName(name);
        user.setPassword(encPassword);
        user.setRoles("ROLE_USER");
        Employee_adminDao.joinUser(user);
    }

    public String login(User user){
      String name = user.getName();
      String token = null;
      if(token == null){
          token = JwtTokenUtil.createToken(name);
          user.setName(name);
          user.setToken(token);
          Employee_adminDao.insertToken(user);
          return token;
      }
      return null;
    }

    public List<User> getAllUsers(){
       return Employee_adminDao.getAllUsers();
    }

    public User getUser(String username){
       return Employee_adminDao.getUser(username);
    }

    public int update(User user){
        String encPassword = encoder.encode(user.getPassword());
        user.setName(user.getName());
        user.setPassword(encPassword);

        int result=0;
        if(user.getPassword() !=null){
            result = Employee_adminDao.updateUser(user);
        }
        return result == 1 ? 1:0;
    }

    public void insertMemo(User user){
        Employee_adminDao.insertMemo(user);
    }

    public void getMemo(String userName){
        Employee_adminDao.getUser(userName);
    }

    public int insertToken(User user){
        if(Employee_adminDao.insertToken(user) == 1){
            return 1;
        }else {
            return 0;
        }
    }

    public String newPwdEncoding(User user){
        String encNewPwd = encoder.encode(user.getPassword());
        user.setPassword(encNewPwd);
        return user.getPassword();
    }

    public boolean comparePwd(User user){
        boolean isMatch=false;
        if(encoder.matches(user.getPassword(), newPwdEncoding(user))){
            isMatch=true;
        }
        return isMatch;
    }


}
