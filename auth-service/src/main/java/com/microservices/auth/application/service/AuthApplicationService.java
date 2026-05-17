package com.microservices.auth.application.service;

import com.microservices.auth.application.dto.request.LoginRequest;
import com.microservices.auth.application.dto.request.RegisterRequest;
import com.microservices.auth.application.dto.response.AuthResponse;
import com.microservices.auth.application.dto.response.UserResponse;
import com.microservices.auth.application.mapper.UserMapper;
import com.microservices.auth.domain.exception.DomainException;
import com.microservices.auth.domain.model.User;
import com.microservices.auth.domain.service.UserDomainService;
import com.microservices.auth.infrastructure.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(AuthApplicationService.class);
    
    private final UserDomainService userDomainService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthApplicationService(UserDomainService userDomainService, 
                                   JwtTokenProvider jwtTokenProvider) {
        this.userDomainService = userDomainService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    /**
     * Register a new user - NOW RETURNS TOKEN!
     * Auto-login after registration
     */
    public AuthResponse register(RegisterRequest request) {
        log.info("Processing registration request for email: {}", request.getEmail());
        
        try {
            // Create user in domain
            User user = userDomainService.register(
                request.getEmail(),
                request.getUsername(),
                request.getPassword()
            );
            
            // Generate JWT token immediately (auto-login)
            String token = jwtTokenProvider.generateToken(
                user.getId().getValue().toString(),
                user.getEmail().getValue()
            );
            
            // Build response WITH token
            AuthResponse response = new AuthResponse();
            response.setToken(token);  // ← NOW HAS TOKEN!
            response.setUserId(user.getId().getValue().toString());
            response.setUsername(user.getUsername().getValue());
            response.setEmail(user.getEmail().getValue());
            response.setMessage("User registered and logged in successfully");
            
            log.info("User registered successfully: {}", user.getId());
            return response;
            
        } catch (DomainException e) {
            log.error("Registration failed: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Login user
     */
    public AuthResponse login(LoginRequest request) {
        log.info("Processing login request for email: {}", request.getEmail());
        
        try {
            User user = userDomainService.login(
                request.getEmail(),
                request.getPassword()
            );
            
            String token = jwtTokenProvider.generateToken(
                user.getId().getValue().toString(),
                user.getEmail().getValue()
            );
            
            AuthResponse response = AuthResponse.forLogin(
                token,
                user.getId().getValue().toString(),
                user.getUsername().getValue(),
                user.getEmail().getValue()
            );
            
            log.info("Login successful for user: {}", user.getId());
            return response;
            
        } catch (DomainException e) {
            log.error("Login failed: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Get user by ID
     */
    public UserResponse getUserById(String userId) {
        log.info("Fetching user profile: {}", userId);
        
        try {
            User user = userDomainService.findById(userId);
            return UserMapper.toResponse(user);
            
        } catch (DomainException e) {
            log.error("Failed to fetch user: {}", e.getMessage());
            throw e;
        }
    }
}
