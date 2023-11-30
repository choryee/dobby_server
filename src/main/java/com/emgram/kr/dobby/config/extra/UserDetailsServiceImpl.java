//package com.emgram.kr.dobby.config.extra;
//
//import com.emgram.kr.dobby.dao.Employee_adminDao;
//import com.emgram.kr.dobby.dto.login.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
////@RequiredArgsConstructor
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private Employee_adminDao Employee_adminDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        //System.out.println("loadUserByUsername> username> "+username );
//        User user =  Employee_adminDao.getUser(username);
//        //System.out.println("loadUserByUsername> user> " + user);
//        if (user == null) {
//            return null;
//        }
//        return new UserDetailsImpl(user);
//    }
//}