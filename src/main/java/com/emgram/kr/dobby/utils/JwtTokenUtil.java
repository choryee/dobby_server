package com.emgram.kr.dobby.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.Date;

@PropertySource("classpath:application.properties")
@Service
public class JwtTokenUtil {
    public static String secretKey;

    public JwtTokenUtil(@Value("${secret-key}") String secretKey){
        this.secretKey=secretKey;
    }
        public static String createToken(String userName){
            String jwtToken = JWT.create()
                    .withSubject("연차")
                    .withExpiresAt(new Date(System.currentTimeMillis()+(60000*60*5))) //60 min*5
                    .withClaim("username", userName)
                    .sign(Algorithm.HMAC512(secretKey));
            return jwtToken;
    }



}
