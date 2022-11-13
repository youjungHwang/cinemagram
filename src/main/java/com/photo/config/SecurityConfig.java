package com.photo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 해당 파일(SecurityConfig)로 시큐리티를 활성화 시킴
@Configuration // IoC에 등록해서 메모리에 뜨게끔
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http)를 삭제 - 기존 시큐리티 기능이 전부 비활성화 된다.

        // [CSRF토큰 해제] 요청을 정상적으로 했는지(페이지 작성) 안(postman)했는지 구분하지 않겠다.
        http.csrf().disable();

        // 직접 설정
        http.authorizeRequests()
            .antMatchers("/","/user/**", "/image/**", "/subscribe/**","/comment/**").authenticated() // 1.해당 URL로 매핑되는건 인증이 필요하고
            .anyRequest().permitAll() // 2.그 외 모든 요청은 허용함
            .and()
            .formLogin()
            .loginPage("/auth/signin") // 1-1. 인증이 필요한 페이지의 경우 /auth/signin으로 이동
            .defaultSuccessUrl("/"); // 1-2. 로그인 정상적으로 성공시 / 페이지로 이동

    }
}
