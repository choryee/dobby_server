package com.emgram.kr.dobby.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtTokenUtil {

//    public static String createToken(String userName, String key, long expireTimeMs){ // 토큰 만들기.
//        Claims claims= Jwts.claims(); //일종의 map
//        claims.put("userName", userName);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis())) //만든 날짜.
//                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//    }
//    public static String makeToken(Authentication authentication){
//        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
//        String jwtToken = JWT.create()
//                .withSubject("cos토큰")
//                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
//                .withClaim("username", principalDetail.getUser().getName())
//                .sign(Algorithm.HMAC512("cos")); // screctkey
//        return jwtToken;
//    }

        public static String createToken(String userName){ // 토큰 만들기.
            String jwtToken = JWT.create()
                    .withSubject("cos토큰")
                    .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                    .withClaim("username", userName)
                    .sign(Algorithm.HMAC512("cos")); // screctkey
            return jwtToken;
    }



}
