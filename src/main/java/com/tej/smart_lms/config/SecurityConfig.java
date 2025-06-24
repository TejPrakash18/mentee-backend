package com.tej.smart_lms.config;

import com.tej.smart_lms.utils.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()  // uses your global or profile-based CorsConfig
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated() // secure everything else
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
