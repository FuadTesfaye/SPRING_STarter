package com.school.auth.application.service;

import com.school.auth.application.port.EventPublisher;
import com.school.auth.application.port.UserRepository;
import com.school.auth.domain.entity.User;
import com.school.auth.domain.event.UserRegisteredEvent;
import com.school.auth.presentation.dto.RegisterRequest;
import com.school.auth.presentation.dto.LoginRequest;
import com.school.auth.presentation.dto.AuthResponse;
import com.school.auth.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User(
            request.getUsername(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            "ROLE_USER"
        );
        User saved = userRepository.save(user);

        // Publish domain event
        UserRegisteredEvent event = new UserRegisteredEvent(
            String.valueOf(saved.getId()),
            saved.getUsername(),
            saved.getEmail()
        );
        eventPublisher.publish("user.registered", event);

        String token = jwtService.generateToken(saved.getUsername());
        return new AuthResponse(token, saved.getUsername(), saved.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }
}
