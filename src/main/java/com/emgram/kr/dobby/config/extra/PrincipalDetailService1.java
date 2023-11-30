package com.emgram.kr.dobby.config.extra;//package com.emgram.kr.dobby.config.auth;
//
//
//import com.emgram.kr.dobby.dao.Employee_adminDao;
//import com.emgram.kr.dobby.dto.login.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class PrincipalDetailService implements UserDetailsService {
//
//    @Autowired
//    private Employee_adminDao Employee_adminDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("PrincipalDetailService의 loadUserByUsername 실행됨...");
//        User principal = Employee_adminDao.getUser(username);
//        System.out.println("PrincipalDetailService의 principal : " + principal);
//        return new PrincipalDetail(principal);
//    }
//}
