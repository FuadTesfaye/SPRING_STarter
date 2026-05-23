package com.ticketbooking.auth.application.usecase;

import com.ticketbooking.auth.application.dto.AuthResponse;
import com.ticketbooking.auth.application.dto.RegisterRequest;
import com.ticketbooking.auth.application.service.JwtTokenService;
import com.ticketbooking.auth.domain.model.User;
import com.ticketbooking.auth.domain.repository.UserRepository;
import com.ticketbooking.auth.infrastructure.email.VerificationEmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final VerificationEmailService verificationEmailService;

    public RegisterUserUseCase(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               JwtTokenService jwtTokenService,
                               VerificationEmailService verificationEmailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.verificationEmailService = verificationEmailService;
    }

    public AuthResponse execute(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        String hash = passwordEncoder.encode(request.password());
        User user = User.create(request.email(), hash, request.fullName());
        User saved = userRepository.save(user);

        verificationEmailService.sendVerification(saved.getEmail(), saved.getFullName(), saved.getVerificationToken());

        String token = jwtTokenService.generateToken(
            saved.getId().toString(), saved.getEmail(), saved.getRole()
        );

        return new AuthResponse(token, saved.getId(), saved.getEmail(), saved.getFullName(), saved.getRole(), false);
    }
}
