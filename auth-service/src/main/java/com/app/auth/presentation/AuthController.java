package com.app.auth.presentation;

import com.app.auth.application.usecases.LoginUseCase;
import com.app.auth.application.usecases.RegisterUserUseCase;
import com.app.auth.domain.User;
import com.app.auth.presentation.dto.LoginRequest;
import com.app.auth.presentation.dto.LoginResponse;
import com.app.auth.presentation.dto.RegisterRequest;
import com.app.auth.presentation.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        User user = registerUserUseCase.execute(request.email(), request.password(), request.fullName());
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getEmail(), user.getFullName()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = loginUseCase.execute(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
