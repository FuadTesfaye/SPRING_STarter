package com.example.authservice.presentation;

import com.example.authservice.application.dto.RegisterRequest;
import com.example.authservice.application.dto.LoginRequest;
import com.example.authservice.application.dto.LoginResponse;
import com.example.authservice.application.service.AuthApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authApplicationService.register(request.getUsername(), request.getPassword());
        return "User registered successfully";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authApplicationService.login(
                request.getUsername(),
                request.getPassword()
        );
    }
}