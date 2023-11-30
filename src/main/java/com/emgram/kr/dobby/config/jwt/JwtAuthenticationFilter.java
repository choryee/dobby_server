package com.emgram.kr.dobby.config.jwt;


import com.emgram.kr.dobby.config.auth.CustomAuthenticationProvider;
import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("logger JwtAuthenticationFilter : 로그인 시도중....");

        try {
            ObjectMapper om =new ObjectMapper();
            User user=om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

            try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
              PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
              return authentication;
            }catch (AuthenticationException e){
                logger.error("Authentication 실패 : " + e.getMessage(), e);
                throw e;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // attemptAuthentication

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {

        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();
        response.setHeader("Authorization", "Bearer "+ JwtTokenUtil.createToken(principalDetail.getUsername()));
    }

}
