package com.emgram.kr.dobby.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.login.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 27강 첨
//시큐리티가 filter를 가지고 있는데, 그 필터중 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때, 위 필터를 무조건 타게 되어있음.
// 만약에 권한이 인증이 필요한 주소가 아니라면, 이 필터를 안 탐.

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private EmployeeDao employeeDao;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, EmployeeDao employeeDao) {
        super(authenticationManager);
        this.employeeDao=employeeDao;
    }

    //인증이나 권한이 필요한 주소 요청이 있을때, 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        System.out.println("인증이나 권한이 필요한 주소가 요청이 됨.");

        String jwtHeader=request.getHeader("Authorization");
        System.out.println("JwtAuthorizationFilter jwtHeader>> "+ jwtHeader);

        //header가 있는지 확인.
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response); // 다시 필터 타게 함
            return;
        }

        //jwt토큰을 검증을 해서 정상적인 사용자인지 확인. 27강.15:44
        String jwtToken=request.getHeader("Authorization").replace("Bearer ", "");

        String username= JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("username").asString();

        if(username !=null){
            User userEntity = employeeDao.getUser(username);
            PrincipalDetail principalDetail=new PrincipalDetail(userEntity);

            //jwt토큰 서명을 통해서, 서명이 정상이면, Authentication를 만든 것.
            Authentication authentication=
                    new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication객체를 저장함.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response); // 22'49 http://localhost:8080/api/v1/users/22 이렇게 비정상적으로 요청하면, 500에러 날것.
        }
    }
}
