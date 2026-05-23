package com.example.authservice.application.service;

import com.example.authservice.application.port.*;
import com.example.authservice.domain.event.UserRegisteredEvent;
import com.example.authservice.domain.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RegisterUserUseCase {
    private final UserRepositoryPort users;
    private final PasswordHasherPort hasher;
    private final EventPublisherPort publisher;

    public RegisterUserUseCase(UserRepositoryPort users, PasswordHasherPort hasher, EventPublisherPort publisher) {
        this.users = users; this.hasher = hasher; this.publisher = publisher;
    }

    public User register(String email, String rawPassword) {
        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already registered");
        User u = new User(UUID.randomUUID(), email, hasher.hash(rawPassword), Instant.now());
        User saved = users.save(u);
        publisher.publish("user.registered",
            new UserRegisteredEvent(saved.getId(), saved.getEmail(), Instant.now()));
        return saved;
    }
}
