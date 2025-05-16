package com.tej.smart_lms.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/**",
                        "/api/projects/**",
                        "/api/user/**",
                        "api/dsa/**"
                ).permitAll() // ✅ public routes for your app
                .anyRequest().permitAll(); // ✅ allow all other routes
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
