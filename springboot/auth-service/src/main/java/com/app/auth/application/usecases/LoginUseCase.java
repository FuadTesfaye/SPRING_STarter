package com.app.auth.application.usecases;

import com.app.auth.application.ports.PasswordService;
import com.app.auth.application.ports.TokenService;
import com.app.auth.domain.User;
import com.app.auth.domain.UserRepository;

public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public LoginUseCase(UserRepository userRepository, PasswordService passwordService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    public String execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordService.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return tokenService.generateToken(user);
    }
}
