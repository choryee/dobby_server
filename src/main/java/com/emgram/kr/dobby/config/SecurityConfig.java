package com.emgram.kr.dobby.config;


import com.emgram.kr.dobby.config.auth.PrincipalDetailService;
import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
import com.emgram.kr.dobby.dao.Employee_adminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsConfig corsConfig;

    @Autowired
    Employee_adminDao Employee_adminDao;

    @Autowired
    PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), Employee_adminDao))

                        .authorizeRequests(authorize -> authorize.antMatchers("/api/v1/users/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/manager/**")
                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/admin/**")
                        .access("hasRole('ROLE_ADMIN')")

                        .anyRequest().permitAll());

    }
}

