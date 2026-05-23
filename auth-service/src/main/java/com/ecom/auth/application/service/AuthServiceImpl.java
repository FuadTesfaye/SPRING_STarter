package com.ecom.auth.application.service;

import com.ecom.auth.application.dto.*;
import com.ecom.auth.application.port.*;
import com.ecom.auth.domain.model.User;
import com.ecom.auth.domain.repository.UserRepository;
import java.util.UUID;

public class AuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;
    private final EventPublisher eventPublisher;

    public AuthServiceImpl(UserRepository userRepository, 
                           PasswordEncoderPort passwordEncoder, 
                           TokenProviderPort tokenProvider,
                           EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.eventPublisher = eventPublisher;
    }

    public AuthResponse register(RegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
            UUID.randomUUID(),
            request.username(),
            request.email(),
            passwordEncoder.encode(request.password())
        );
        user.validate();

        User savedUser = userRepository.save(user);

        // Publish event
        eventPublisher.publishUserRegistered(new UserRegisteredEvent(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail()
        ));

        String token = tokenProvider.generateToken(savedUser);
        return new AuthResponse(savedUser.getId(), savedUser.getUsername(), token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user);
        return new AuthResponse(user.getId(), user.getUsername(), token);
    }
}
