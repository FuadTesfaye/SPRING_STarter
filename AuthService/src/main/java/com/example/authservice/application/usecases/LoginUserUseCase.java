package com.example.authservice.application.usecases;

import com.example.authservice.application.dto.request.LoginUserRequest;
import com.example.authservice.application.dto.response.LoginUserResponse;
import com.example.authservice.application.handlers.UnauthorizedException;
import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.interfaces.UserRepository;
import com.example.authservice.domain.services.AuthDomainService;


public class LoginUserUseCase implements LoginUserService {

    private final UserRepository userRepository;
    private final AuthDomainService authDomainService;

    public LoginUserUseCase(UserRepository userRepository, AuthDomainService authDomainService) {
        this.userRepository = userRepository;
        this.authDomainService = authDomainService;
    }

    public LoginUserResponse execute(LoginUserRequest request) {
        validate(request);
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!user.passwordMatches(request.password())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return new LoginUserResponse(
                authDomainService.issueToken(),
                "LOGIN_SUCCESS",
                "Authentication successful"
        );
    }

    private void validate(LoginUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
    }
}
