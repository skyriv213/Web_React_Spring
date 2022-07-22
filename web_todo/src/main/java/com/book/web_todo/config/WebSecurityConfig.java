package com.book.web_todo.config;

import com.book.web_todo.security.JwtAuthenticationFilter;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors()
                .and()
                .csrf()// csrf는 현재 사용하지 않으므로 disable
                    .disable()
                .httpBasic() // token을 사용하므로 basic 인증 disable
                    .disable()
                .sessionManagement() // session기반이 아님
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(
                        "/", "/auth/**" // 경로는 인증을 안해도 됨
                    ).permitAll()
                .anyRequest()// 위의 antMatcher의 경로를 제외하고는 모든 경로는 인증해야함
                    .authenticated();

        // filter 등록
        // 매 요청마다
        // CorsFilter 실행 후
        // jwtAutenticationFilter를 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );

    }
}
