package com.example.auth.presentation.controller;

import com.example.auth.application.dto.AuthResponse;
import com.example.auth.application.dto.LoginCommand;
import com.example.auth.application.dto.RegisterUserCommand;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.application.service.AuthApplicationService;
import com.example.auth.presentation.request.LoginRequest;
import com.example.auth.presentation.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authApplicationService.register(new RegisterUserCommand(
                request.username(),
                request.email(),
                request.password()
        ));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authApplicationService.login(new LoginCommand(
                request.username(),
                request.password()
        ));
    }
}
