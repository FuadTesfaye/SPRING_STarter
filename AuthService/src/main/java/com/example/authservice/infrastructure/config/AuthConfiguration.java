package com.example.authservice.infrastructure.config;

import com.example.authservice.application.usecases.LoginUserService;
import com.example.authservice.application.usecases.LoginUserUseCase;
import com.example.authservice.application.usecases.RegisterUserService;
import com.example.authservice.application.usecases.RegisterUserUseCase;
import com.example.authservice.domain.interfaces.UserRepository;
import com.example.authservice.domain.services.AuthDomainService;
import com.example.authservice.infrastructure.persistence.adapter.UserPersistenceAdapter;
import com.example.authservice.infrastructure.persistence.adapter.UserPersistenceMapper;
import com.example.authservice.infrastructure.persistence.repository.SpringDataUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Bean
    public AuthDomainService authDomainService() {
        return new AuthDomainService();
    }

    @Bean
    public UserPersistenceMapper userPersistenceMapper() {
        return new UserPersistenceMapper();
    }

    @Bean
    public UserRepository userRepository(
            SpringDataUserRepository springDataUserRepository,
            UserPersistenceMapper userPersistenceMapper
    ) {
        return new UserPersistenceAdapter(springDataUserRepository, userPersistenceMapper);
    }

    @Bean
    public RegisterUserService registerUserUseCase(UserRepository userRepository, AuthDomainService authDomainService) {
        return new RegisterUserUseCase(userRepository, authDomainService);
    }

    @Bean
    public LoginUserService loginUserUseCase(UserRepository userRepository, AuthDomainService authDomainService) {
        return new LoginUserUseCase(userRepository, authDomainService);
    }
}
