package com.app.auth.infrastructure.config;

import com.app.auth.application.ports.AuthEventPublisher;
import com.app.auth.application.ports.PasswordService;
import com.app.auth.application.ports.TokenService;
import com.app.auth.application.usecases.LoginUseCase;
import com.app.auth.application.usecases.RegisterUserUseCase;
import com.app.auth.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, PasswordService passwordService, AuthEventPublisher eventPublisher) {
        return new RegisterUserUseCase(userRepository, passwordService, eventPublisher);
    }

    @Bean
    public LoginUseCase loginUseCase(UserRepository userRepository, PasswordService passwordService, TokenService tokenService) {
        return new LoginUseCase(userRepository, passwordService, tokenService);
    }
}
