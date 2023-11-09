package com.emgram.kr.dobby.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

//24강
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중.");

        // 1. username, password 받아서
        try {
//            BufferedReader br =request.getReader();
//            String input=null;
//            while((input=br.readLine()) !=null){
//                System.out.println(input);
//            }
            ObjectMapper om =new ObjectMapper();
            User user=om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername()함수가 실행됨.
            // authentication. 여기에 내 로그인 정보 있음. 그것은 서버의 세션영역에 저장돼, 있어 그 정보가 있다는 것은 로그인 됐다는 의미임.
            // authentication객체안 - Principal 안 - Username정보
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : "+ principalDetail.getUsername());
            System.out.println("=================================================");
            //authentication객체가 세션영역에 저장을 해야하고, 그 방법이 밑처럼 리턴해주면 됨.
            // 리턴의 이뉴는 뤈한 관리를 시큐리티가 대신 해주기 때문에 편할려고
            // 굳이 jwt토큰을 사용하면서 세션을 만들 이유가 없음. 근데, 단지 권한 처리때문에 세션에 넣어주는 것.

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // attemptAuthentication실행후 인증이 정상적으로 되었으면, 밑 함수가 실행됨
    // 여기서 jwt토큰을 만들어서 request요청한 사용자에게 그 토큰을 response해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료됐다는 의미임.");
        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("username", principalDetail.getUser().getName())
                .sign(Algorithm.HMAC512("cos")); // screctkey

        System.out.println("response로 토큰 보내기 token>> "+jwtToken);
        //response.addHeader("Authorization", "Bearer "+ jwtToken);
        response.setHeader("Authorization", "Bearer "+ jwtToken);
    }
}
