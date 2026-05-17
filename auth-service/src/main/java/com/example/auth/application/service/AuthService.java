// auth-service/src/main/java/com/example/auth/application/service/AuthService.java
package com.example.auth.application.service;

import com.example.auth.application.dto.*;
import com.example.auth.application.exception.*;
import com.example.auth.domain.event.EventPublisher;
import com.example.auth.domain.event.UserRegisteredEvent;
import com.example.auth.domain.model.User;
import com.example.auth.domain.repository.UserRepository;
import com.example.auth.infrastructure.security.JwtTokenProvider;
import com.example.auth.infrastructure.security.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EventPublisher eventPublisher;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      JwtTokenProvider jwtTokenProvider,
                      EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.eventPublisher = eventPublisher;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        User user = new User.Builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);

        // Publish domain event
        UserRegisteredEvent event = new UserRegisteredEvent(
            user.getId(), user.getUsername(), user.getEmail()
        );
        eventPublisher.publishUserRegistered(event);

        String token = jwtTokenProvider.generateToken(user.getId().toString(), user.getUsername());
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getId().toString(), user.getUsername());
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }

    public UserResponse validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException("Invalid or expired token");
        }
        
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}