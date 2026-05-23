package com.ticketbooking.auth.application.usecase;

import com.ticketbooking.auth.application.dto.AuthResponse;
import com.ticketbooking.auth.application.dto.LoginRequest;
import com.ticketbooking.auth.application.service.JwtTokenService;
import com.ticketbooking.auth.domain.model.User;
import com.ticketbooking.auth.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public LoginUserUseCase(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthResponse execute(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (user.isBanned()) {
            throw new IllegalArgumentException("Your account has been suspended. Contact support.");
        }

        if (!user.isVerified()) {
            throw new IllegalArgumentException("Please verify your email before logging in. Check your inbox.");
        }

        String token = jwtTokenService.generateToken(
            user.getId().toString(), user.getEmail(), user.getRole()
        );

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), user.getRole(), true);
    }
}
