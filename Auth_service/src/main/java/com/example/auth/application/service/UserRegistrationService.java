package com.example.auth.application.service;

import com.example.auth.domain.model.User;
import com.example.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegistrationService {

    private final UserRepository userRepository;

    public User registerUser(String fullName, String email, String password) {

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);

        if (!user.isValidForRegistration()) {
            throw new IllegalArgumentException("Invalid registration data. Email required and password at least 6 characters.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered!");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        System.out.println("✅ User registered successfully: " + email);
        return savedUser;
    }
}