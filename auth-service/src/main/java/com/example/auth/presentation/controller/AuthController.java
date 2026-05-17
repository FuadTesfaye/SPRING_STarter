package com.example.auth.presentation.controller;

import com.example.auth.application.service.AuthService;
import com.example.auth.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "User registration endpoints")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Register a new user",
        responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
        }
    )
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println("REGISTER ENDPOINT HIT");
        return authService.register(user);
    }

    @GetMapping("/status")
    public String status() {
        return "Auth Service Running";
    }
}
