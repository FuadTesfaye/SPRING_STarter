package com.app.auth.application.usecases;

import com.app.auth.application.ports.AuthEventPublisher;
import com.app.auth.application.ports.PasswordService;
import com.app.auth.domain.User;
import com.app.auth.domain.UserRepository;
import java.util.UUID;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final AuthEventPublisher eventPublisher;

    public RegisterUserUseCase(UserRepository userRepository, PasswordService passwordService, AuthEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.eventPublisher = eventPublisher;
    }

    public User execute(String email, String password, String fullName) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        
        String hash = passwordService.hash(password);
        User user = new User(UUID.randomUUID(), email, hash, fullName);
        
        User savedUser = userRepository.save(user);
        eventPublisher.publishUserRegistered(savedUser);
        
        return savedUser;
    }
}
