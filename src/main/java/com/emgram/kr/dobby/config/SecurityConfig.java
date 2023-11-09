package com.emgram.kr.dobby.config;


import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.emgram.kr.dobby.config.auth.PrincipalDetailService;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Autowired
    PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인 해주는데, password를 가로채기 하는데
    // 해당 Password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 안 겠다 의미
                .and()
                    .addFilter(corsFilter) //@CrossOrgin은 인증이 필요없는 매소드에서 걸어주는 것.
                    .formLogin().disable() //로그인폼 사용 안 함 의미
                    .httpBasic().disable() // id,pwd를 헤더에 넣어서 보내는 것.21강. <-> Bearer방식(토큰방식)
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager()))

                    .authorizeRequests() //이 밑은 권한이 필요한 것.
                    //.antMatchers("/api/v1/users/**").permitAll()
                    .antMatchers("/api/v1/users/**");
                    //.anyRequest().authenticated();
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login")
//                    .permitAll();
    }
}

