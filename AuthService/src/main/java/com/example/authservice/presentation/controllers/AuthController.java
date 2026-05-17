package com.example.authservice.presentation.controllers;

import com.example.authservice.application.usecases.LoginUserService;
import com.example.authservice.application.usecases.RegisterUserService;
import com.example.authservice.presentation.controllers.request.LoginRequest;
import com.example.authservice.presentation.controllers.request.RegisterRequest;
import com.example.authservice.presentation.controllers.response.AuthResponse;
import com.example.authservice.presentation.controllers.response.UserResponse;
import com.example.authservice.presentation.mapper.AuthPresentationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserService registerUserUseCase;
    private final LoginUserService loginUserUseCase;
    private final AuthPresentationMapper authPresentationMapper;

    public AuthController(
            RegisterUserService registerUserUseCase,
            LoginUserService loginUserUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.authPresentationMapper = new AuthPresentationMapper();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authPresentationMapper.toPresentation(
                registerUserUseCase.execute(authPresentationMapper.toApplication(request))
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authPresentationMapper.toPresentation(
                loginUserUseCase.execute(authPresentationMapper.toApplication(request))
        );
    }
}
