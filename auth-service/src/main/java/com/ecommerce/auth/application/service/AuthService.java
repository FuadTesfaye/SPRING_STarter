package com.ecommerce.auth.application.service;

import com.ecommerce.auth.application.port.EventPublisher;
import com.ecommerce.auth.domain.event.UserRegisteredEvent;
import com.ecommerce.auth.domain.model.User;
import com.ecommerce.auth.domain.repository.UserRepository;
import com.ecommerce.auth.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public User register(String email, String username, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken: " + username);
        }

        User user = User.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build();

        User saved = userRepository.save(user);
        log.info("User registered: {}", saved.getEmail());

        eventPublisher.publish("user.registered", UserRegisteredEvent.builder()
                .userId(saved.getId())
                .email(saved.getEmail())
                .username(saved.getUsername())
                .timestamp(LocalDateTime.now())
                .build());

        return saved;
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        log.info("User logged in: {}", email);
        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
    }
}
