package com.emgram.kr.dobby.config.jwt;


import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중.");


        try {
            ObjectMapper om =new ObjectMapper();
            User user=om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

             Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : "+ principalDetail.getUsername());
            System.out.println("=================================================");
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // attemptAuthentication

    @Override
   // protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {

        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();

        System.out.println("response로 토큰 보내기 token>> "+JwtTokenUtil.createToken(principalDetail.getUsername()));
        response.setHeader("Authorization", "Bearer "+ JwtTokenUtil.createToken(principalDetail.getUsername()));
    }
}
