package com.emgram.kr.dobby.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtTokenUtil {

        public static String createToken(String userName){ // 토큰 만들기.
            String jwtToken = JWT.create()
                    .withSubject("cos토큰")
                    .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                    .withClaim("username", userName)
                    .sign(Algorithm.HMAC512("cos"));
            return jwtToken;
    }



}
