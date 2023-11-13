package com.emgram.kr.dobby.config;


import com.emgram.kr.dobby.config.auth.PrincipalDetailService;
import com.emgram.kr.dobby.config.jwt.JwtAuthenticationFilter;
import com.emgram.kr.dobby.config.jwt.JwtAuthorizationFilter;
import com.emgram.kr.dobby.dao.EmployeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsConfig corsConfig;

    @Autowired
    EmployeeDao employeeDao;

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
                    .addFilter(corsConfig.corsFilter()) //@CrossOrgin은 인증이 필요없는 매소드에서 걸어주는 것.
                    .formLogin().disable() //로그인폼 사용 안 함 의미
                    .httpBasic().disable() // id,pwd를 헤더에 넣어서 보내는 것.21강. <-> Bearer방식(토큰방식)
                    .addFilter(new JwtAuthenticationFilter(authenticationManager())) //Authentication : 인증. 첨 로그인 할때, 토큰생성함.
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), employeeDao)) // Authorization :인가. 인증필요한 페이지에 도큰들고, 다시 요청들어올때.

                    //.authorizeRequests() //URL 패턴에 대한 접근 권한을 설정하는 메서드입니다
                    //.antMatchers("/api/v1/users/**").authenticated();
                        .authorizeRequests(authorize -> authorize.antMatchers("/api/v1/users/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/manager/**")
                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                        .antMatchers("/api/v1/users/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll());


//                    .anyRequest() //다른 모든 요청은 인증이 필요합니다. 즉, 로그인한 사용자만 접근할 수 있습니다.
//                    .authenticated("/api/v1/users/**");
                    //.antMatchers("/api/v1/users/**"); //특정 URL 패턴에 대한 접근 권한을 설정합니다.

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

