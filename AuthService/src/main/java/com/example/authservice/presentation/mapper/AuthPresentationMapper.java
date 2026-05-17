package com.example.authservice.presentation.mapper;

import com.example.authservice.application.dto.request.LoginUserRequest;
import com.example.authservice.application.dto.request.RegisterUserRequest;
import com.example.authservice.application.dto.response.LoginUserResponse;
import com.example.authservice.application.dto.response.RegisterUserResponse;
import com.example.authservice.presentation.controllers.request.LoginRequest;
import com.example.authservice.presentation.controllers.request.RegisterRequest;
import com.example.authservice.presentation.controllers.response.AuthResponse;
import com.example.authservice.presentation.controllers.response.UserResponse;

public class AuthPresentationMapper {

    public RegisterUserRequest toApplication(RegisterRequest request) {
        return new RegisterUserRequest(request.name(), request.email(), request.password());
    }

    public LoginUserRequest toApplication(LoginRequest request) {
        return new LoginUserRequest(request.email(), request.password());
    }

    public UserResponse toPresentation(RegisterUserResponse response) {
        return new UserResponse(response.userId(), response.name(), response.email(), response.status());
    }

    public AuthResponse toPresentation(LoginUserResponse response) {
        return new AuthResponse(response.token(), response.status(), response.message());
    }
}
