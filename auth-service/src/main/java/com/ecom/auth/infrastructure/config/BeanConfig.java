package com.ecom.auth.infrastructure.config;

import com.ecom.auth.application.port.EventPublisher;
import com.ecom.auth.application.port.PasswordEncoderPort;
import com.ecom.auth.application.port.TokenProviderPort;
import com.ecom.auth.application.service.AuthServiceImpl;
import com.ecom.auth.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AuthServiceImpl authService(
            UserRepository userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenProviderPort tokenProvider,
            EventPublisher eventPublisher) {
        return new AuthServiceImpl(userRepository, passwordEncoder, tokenProvider, eventPublisher);
    }
}
