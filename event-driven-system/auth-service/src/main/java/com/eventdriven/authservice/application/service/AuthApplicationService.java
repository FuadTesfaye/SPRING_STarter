package com.eventdriven.authservice.application.service;

import com.eventdriven.authservice.application.dto.*;
import com.eventdriven.authservice.domain.model.User;
import com.eventdriven.authservice.domain.service.UserDomainService;
import com.eventdriven.authservice.infrastructure.messaging.RabbitMQSender;
import com.eventdriven.authservice.infrastructure.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthApplicationService {
    
    private final UserDomainService userDomainService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RabbitMQSender rabbitMQSender;
    
    public AuthApplicationService(UserDomainService userDomainService, JwtTokenProvider jwtTokenProvider, RabbitMQSender rabbitMQSender) {
        this.userDomainService = userDomainService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.rabbitMQSender = rabbitMQSender;
    }
    
    public AuthResponse register(RegisterRequest request) {
        User user = userDomainService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName()
        );
        
       // UserRegisteredEvent event = UserRegisteredEvent.builder()
         //       .userId(user.getId())
           //     .username(user.getUsername())
             //   .email(user.getEmail())
               // .fullName(user.getFullName())
                //.timestamp(LocalDateTime.now())
                //.build();
        
        //rabbitMQSender.sendUserRegisteredEvent(event);
        
        String token = jwtTokenProvider.generateToken(user.getUsername());
        
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }
    
    public AuthResponse login(LoginRequest request) {
        User user = userDomainService.findByUsername(request.getUsername());
        
        if (!userDomainService.validatePassword(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtTokenProvider.generateToken(user.getUsername());
        
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }
    
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
    
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }
}