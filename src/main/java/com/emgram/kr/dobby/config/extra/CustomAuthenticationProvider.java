//package com.emgram.kr.dobby.config.extra;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@RequiredArgsConstructor
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//
//        // 입력한 ID, Password 조회
//        String userId = token.getName();
//        String userPw = (String) token.getCredentials();
//
//        // UserDetailsService를 통해 DB에서 조회한 사용자
//        UserDetailsImpl dbUser = (UserDetailsImpl) userDetailsService.loadUserByUsername(userId);
//
//        logger.debug("token: {}", token);
//        logger.debug("userId: {}", userId);
//        logger.debug("userPw: {}", userPw);
//        logger.debug("dbUser: {}", dbUser);
//
//        // 비밀번호 매칭되는지 확인
//        if (!passwordEncoder.matches(userPw, dbUser.getPassword())) {
//            logger.debug("Invalid password for user: {}", dbUser.getUsername());
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        // 사용자 인증이 성공했을 때 새로운 UsernamePasswordAuthenticationToken 생성
//        return new UsernamePasswordAuthenticationToken(dbUser, null, dbUser.getAuthorities());
//    }
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}