package com.ums3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTResponseFilter jwtResponseFilter;

    public SecurityConfig(JWTResponseFilter jwtResponseFilter) {
        this.jwtResponseFilter = jwtResponseFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
       httpSecurity.csrf().disable().cors().disable();
       httpSecurity.addFilterBefore(jwtResponseFilter, AuthorizationFilter.class);
        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
//                .requestMatchers("/Api/v3/Auth/AddUser","/Api/v3/Auth/login")
//                .permitAll()
//                .requestMatchers("/Api/v3/Auth/AddCountry").hasRole("ADMIN")
//                .requestMatchers("/Api/v3/Auth/profile").hasAnyRole("ADMIN","USER")
//                .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
