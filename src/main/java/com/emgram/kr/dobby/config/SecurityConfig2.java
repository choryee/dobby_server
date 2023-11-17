//package com.emgram.kr.dobby.config;
//
//
//import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
//import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
//import com.emgram.kr.dobby.dao.Employee_adminDao;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import com.emgram.kr.dobby.config.auth.PrincipalDetailService;
//import org.springframework.web.filter.CorsFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final CorsFilter corsFilter;
//
//    @Autowired
//    Employee_adminDao Employee_adminDao;
//
//    @Autowired
//    PrincipalDetailService principalDetailService;
//
//    @Bean
//    public BCryptPasswordEncoder encoderPWD(){
//        return new BCryptPasswordEncoder();
//    }
//
//    // 시큐리티가 대신 로그인 해주는데, password를 가로채기 하는데
//    // 해당 Password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
//    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                    .cors().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), Employee_adminDao))
//
//                .authorizeRequests(authroize -> authroize.antMatchers("/api/v1/users/**")
//                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//
//                        .antMatchers("/api/v1/manager/**")
//                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//
//                        .antMatchers("/api/v1/admin/**")
//                        .access("hasRole('ROLE_ADMIN')")
//                        .anyRequest().permitAll())
//                .build();
//    }
//}
//
