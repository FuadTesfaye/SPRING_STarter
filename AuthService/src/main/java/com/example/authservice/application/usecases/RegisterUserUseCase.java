package com.example.authservice.application.usecases;

import com.example.authservice.application.dto.request.RegisterUserRequest;
import com.example.authservice.application.dto.response.RegisterUserResponse;
import com.example.authservice.application.handlers.ConflictException;
import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.interfaces.UserRepository;
import com.example.authservice.domain.services.AuthDomainService;
import com.example.authservice.application.usecases.RegisterUserService;

public class RegisterUserUseCase implements RegisterUserService {

    private final UserRepository userRepository;
    private final AuthDomainService authDomainService;

    public RegisterUserUseCase(UserRepository userRepository, AuthDomainService authDomainService) {
        this.userRepository = userRepository;
        this.authDomainService = authDomainService;
    }

    public RegisterUserResponse execute(RegisterUserRequest request) {
        validate(request);
        userRepository.findByEmail(request.email()).ifPresent(existing -> {
            throw new ConflictException("User with email " + request.email() + " already exists");
        });

        User savedUser = userRepository.save(
                authDomainService.registerUser(request.name(), request.email(), request.password())
        );

        return new RegisterUserResponse(
                savedUser.id(),
                savedUser.name(),
                savedUser.email(),
                savedUser.status().name()
        );
    }

    private void validate(RegisterUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
    }
}
