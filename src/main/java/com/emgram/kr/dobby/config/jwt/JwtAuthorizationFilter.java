package com.emgram.kr.dobby.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private Employee_adminDao Employee_adminDao;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, Employee_adminDao Employee_adminDao) {
        super(authenticationManager);
        this.Employee_adminDao=Employee_adminDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        System.out.println("인증이나 권한이 필요한 주소가 요청이 됨.");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("JwtAuthorizationFilter jwtHeader>> "+ jwtHeader);


        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        // 쿨에서 받은 헤더의 토큰을 받기
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        //위 토큰을 검증함.밑.
        String username =
                JWT.require(Algorithm.HMAC512(JwtTokenUtil.secretKey)).build().verify(jwtToken).getClaim("username").asString();

        if(username !=null){
            User userEntity = Employee_adminDao.getUser(username);
            PrincipalDetail principalDetail = new PrincipalDetail(userEntity);

            Authentication authentication=
                    new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("SecurityContextHolder.getContext().getAuthentication() JwtAuthorizationFilter>> "+ SecurityContextHolder.getContext().getAuthentication());

            chain.doFilter(request,response);
        } // if

    }// doFilterInternal

}
