package com.example.authservice.application.service;

import com.example.authservice.application.dto.*;
import com.example.authservice.domain.model.User;
import com.example.authservice.domain.repository.UserRepository;
import com.example.authservice.infrastructure.messaging.AuthEventPublisher;
import com.example.authservice.infrastructure.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Add this

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       AuthEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }

    public String register(RegisterRequest request) {

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        // CHANGE THIS LINE - hash the password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        UserRegisteredEvent event =
                new UserRegisteredEvent(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                );

        eventPublisher.publishUserRegistered(event);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // CHANGE THIS LINE - compare using password encoder
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}