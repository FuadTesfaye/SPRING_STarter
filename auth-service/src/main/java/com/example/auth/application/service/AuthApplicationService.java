package com.example.auth.application.service;

import com.example.auth.application.dto.AuthResponse;
import com.example.auth.application.dto.LoginCommand;
import com.example.auth.application.dto.RegisterUserCommand;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.application.exception.InvalidCredentialsException;
import com.example.auth.application.exception.UserAlreadyExistsException;
import com.example.auth.application.port.out.PasswordHasher;
import com.example.auth.application.port.out.TokenProvider;
import com.example.auth.application.port.out.UserEventPublisher;
import com.example.auth.application.port.out.UserRepository;
import com.example.auth.domain.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthApplicationService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;
    private final UserEventPublisher userEventPublisher;

    public AuthApplicationService(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            TokenProvider tokenProvider,
            UserEventPublisher userEventPublisher
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
        this.userEventPublisher = userEventPublisher;
    }

    @Transactional
    public UserResponse register(RegisterUserCommand command) {
        validateUniqueness(command.username(), command.email());

        User savedUser = userRepository.save(new User(
                null,
                command.username(),
                command.email(),
                passwordHasher.hash(command.password())
        ));

        // Publish event asynchronously (non-blocking)
        publishUserRegisteredAsync(savedUser);

        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Async
    private void publishUserRegisteredAsync(User user) {
        try {
            userEventPublisher.publishUserRegistered(user);
            System.out.println("✅ User registered event published successfully");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish user registered event: " + e.getMessage());
            // Don't throw - event publishing failure shouldn't fail registration
        }
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginCommand command) {
        User user = userRepository.findByUsername(command.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return new AuthResponse(user.getId(), user.getUsername(), tokenProvider.generateToken(user));
    }

    private void validateUniqueness(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }
    }
}
