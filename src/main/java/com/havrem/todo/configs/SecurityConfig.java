package com.havrem.todo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())       // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()       // allow ALL requests
                )
                .httpBasic(httpBasic -> {})         // optional: allows curl/basic auth
                .formLogin(form -> form.disable()); // disable login form

        return http.build();
    }
}
