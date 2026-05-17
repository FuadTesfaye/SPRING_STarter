package com.microservices.auth.infrastructure.config;

import com.microservices.auth.domain.event.EventPublisher;
import com.microservices.auth.domain.repository.UserRepository;
import com.microservices.auth.domain.service.UserDomainService;
import com.microservices.auth.application.service.AuthApplicationService;
import com.microservices.auth.infrastructure.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BEAN CONFIGURATION
 * 
 * Manually creates Spring beans to keep domain layer pure
 */
@Configuration
public class BeanConfig {
    
    /**
     * Create UserDomainService bean
     */
    @Bean
    public UserDomainService userDomainService(
            UserRepository userRepository, 
            EventPublisher eventPublisher) {
        return new UserDomainService(userRepository, eventPublisher);
    }
    
    /**
     * Create AuthApplicationService bean
     * Now includes JwtTokenProvider for real token generation
     */
    @Bean
    public AuthApplicationService authApplicationService(
            UserDomainService userDomainService,
            JwtTokenProvider jwtTokenProvider) {
        return new AuthApplicationService(userDomainService, jwtTokenProvider);
    }
}
