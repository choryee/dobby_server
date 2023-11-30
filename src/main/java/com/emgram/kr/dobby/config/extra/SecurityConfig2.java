//package com.emgram.kr.dobby.config;
//
//import com.emgram.kr.dobby.config.extra.CustomAuthenticationProvider;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.emgram.kr.dobby.dao.Employee_adminDao;
//import com.emgram.kr.dobby.dto.login.User;
//import com.emgram.kr.dobby.utils.JwtTokenUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    CorsConfig corsConfig;
//
//    @Qualifier("")
//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    Employee_adminDao Employee_adminDao;
//
//    @Bean
//    public BCryptPasswordEncoder encoderPwd() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // CustomAuthenticationProvider 빈 등록
//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider(userDetailsService, encoderPwd());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().
//                requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    http
//            .csrf().disable()
//            .cors();
//            //.addFilter(corsConfig.corsFilter());
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login") // 스프링이 가지고 있는것.
//                .loginPage("/loginForm")
//                .defaultSuccessUrl("/");
//
//        http.logout()
//                .logoutUrl("/logout")
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//                        String jwtHeader=httpServletRequest.getHeader("Authorization");
//                        if(jwtHeader != null){
//                            String jwtToken=jwtHeader.replace("Bearer ", "");
//                            String username =
//                                    JWT.require(Algorithm.HMAC512(JwtTokenUtil.secretKey)).build().verify(jwtToken).getClaim("username").asString();
//                            User user=new User();
//                            user.setName(username);
//                            Employee_adminDao.deleteToken(user);
//                        }else {
//                            return;
//                        }
//                    }
//                })
//                .logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID", "remember-me");
//    }
//
//    //AuthenticationManager에 Provider등록
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider());
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");  // Allow all origins for simplicity; you might want to restrict it in a production environment
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//}
