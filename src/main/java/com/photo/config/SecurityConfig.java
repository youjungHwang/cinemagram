package com.photo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http)

        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers("/","/user/**", "/image/**", "/follow/**","/comment/**","/api/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/auth/signin") // GET
            .loginProcessingUrl("/auth/signin") // POST
            .defaultSuccessUrl("/");

    }
}
