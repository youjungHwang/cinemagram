package com.photo.config;

import com.photo.config.oauth.CustomOAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    private final CustomOAuth2DetailsService customOAuth2DetailsService;


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
                .defaultSuccessUrl("/")
            .and()
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2DetailsService);


    }
}
