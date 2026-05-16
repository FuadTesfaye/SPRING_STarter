package com.example.auth.infrastructure.config;

import com.example.auth.application.port.out.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordHasher passwordHasher(BCryptPasswordEncoder encoder) {
        return new PasswordHasher() {
            @Override
            public String hash(String rawPassword) {
                return encoder.encode(rawPassword);
            }

            @Override
            public boolean matches(String rawPassword, String hashedPassword) {
                return encoder.matches(rawPassword, hashedPassword);
            }
        };
    }
}
