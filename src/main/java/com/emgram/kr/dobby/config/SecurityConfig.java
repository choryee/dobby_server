package com.emgram.kr.dobby.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emgram.kr.dobby.config.auth.PrincipalDetailService;
import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsConfig corsConfig;

    @Autowired
    Employee_adminDao Employee_adminDao;

    @Autowired
    PrincipalDetailService principalDetailService; // o

    @Bean
    public BCryptPasswordEncoder encoderPWD(){ // o
        return new BCryptPasswordEncoder();
    }


    //AuthenticationManager에 Provider등록
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // o
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(corsConfig.corsFilter())
                    .formLogin().disable() //로그인폼 사용 안 함 의미
                    .httpBasic().disable() // id,pwd를 헤더에 넣어서 보내는 것. <-> Bearer방식(토큰방식) // rest api 만을 고려하므로 기본 설정은 해제하겠습니다.

                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), Employee_adminDao))

                    .authorizeRequests(authorize ->
                        authorize.antMatchers("/api/v1/users/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/manager/**")
                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll() //모두 허용
                    );
//                        .authorizeRequests()
//                        .antMatchers("/api/v1/users/join","/login", "/logout").permitAll()
//                        .anyRequest().authenticated();

        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        String jwtHeader=httpServletRequest.getHeader("Authorization");
                        if(jwtHeader != null){
                        String jwtToken=jwtHeader.replace("Bearer ", "");
                        String username =
                                JWT.require(Algorithm.HMAC512(JwtTokenUtil.secretKey)).build().verify(jwtToken).getClaim("username").asString();
                        User user=new User();
                        user.setName(username);
                        Employee_adminDao.deleteToken(user);
                        }
                    }
                })
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "remember-me");
    }
}

