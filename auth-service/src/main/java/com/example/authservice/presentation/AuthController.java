package com.example.authservice.presentation;

import com.example.authservice.application.service.LoginUseCase;
import com.example.authservice.application.service.RegisterUserUseCase;
import com.example.authservice.domain.model.User;
import com.example.authservice.presentation.dto.LoginRequest;
import com.example.authservice.presentation.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUserUseCase register;
    private final LoginUseCase login;
    public AuthController(RegisterUserUseCase register, LoginUseCase login) {
        this.register = register; this.login = login;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User u = register.register(req.email, req.password);
        return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        String token = login.login(req.email, req.password);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
