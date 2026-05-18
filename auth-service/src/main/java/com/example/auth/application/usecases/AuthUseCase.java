package com.example.auth.application.usecases;
import com.example.auth.application.dto.*;
import com.example.auth.application.ports.UserEventPublisher;
import com.example.auth.domain.model.User;
import com.example.auth.domain.repository.UserRepository;
import com.example.auth.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final UserEventPublisher userEventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public void register(RegisterRequest request) {
        User user = new User(null,request.username()
                ,passwordEncoder.encode(request.password()),request.email());
        User savedUser=userRepository.save(user);
//publish event to RabbitMQ
        userEventPublisher.publishUserRegistered(savedUser.getId(),savedUser.getEmail());
    }
    public String login(LoginRequest request) {
        // 1. Find user
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Generate Token (using your JwtService)
        return jwtService.generateToken(user.getEmail());
    }

}
