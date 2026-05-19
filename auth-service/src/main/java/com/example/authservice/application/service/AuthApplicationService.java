package com.example.authservice.application.service;

import com.example.authservice.domain.model.UserEntity;
import com.example.authservice.infrastructure.repository.UserRepository;
import com.example.authservice.infrastructure.messaging.UserEventPublisher;
import com.example.authservice.application.dto.LoginResponse;
import com.example.authservice.infrastructure.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserEventPublisher eventPublisher;
    private final JwtService jwtService;

    public AuthApplicationService(UserRepository userRepository,
                                  BCryptPasswordEncoder encoder,
                                  UserEventPublisher eventPublisher,
                                  JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.eventPublisher = eventPublisher;
        this.jwtService = jwtService;
    }

    public void register(String username, String password) {

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        userRepository.save(user);

        eventPublisher.publish(
                "user.registered",
                username
        );
    }

    public LoginResponse login(String username, String password) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(username);

        return new LoginResponse(token);
    }
}