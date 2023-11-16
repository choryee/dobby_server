package com.emgram.kr.dobby.config.auth;


import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private Employee_adminDao Employee_adminDao;

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함
    // username이 DB에 있는지만 확인해주면 됨.
    // 밑에 Override 된 함수에서 username 확인을 함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailService의 loadUserByUsername 실행됨...");
        User principal = Employee_adminDao.getUser(username);
        System.out.println("PrincipalDetailService의 User : " + principal);
//                .orElseThrow(()->{
//                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
//                });

        return new PrincipalDetail(principal);
    }
}
