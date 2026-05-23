package com.ticketbooking.auth.presentation.controller;

import com.ticketbooking.auth.application.dto.AuthResponse;
import com.ticketbooking.auth.application.dto.LoginRequest;
import com.ticketbooking.auth.application.dto.RegisterRequest;
import com.ticketbooking.auth.application.usecase.LoginUserUseCase;
import com.ticketbooking.auth.application.usecase.RegisterUserUseCase;
import com.ticketbooking.auth.application.usecase.VerifyEmailUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase,
                          LoginUserUseCase loginUserUseCase,
                          VerifyEmailUseCase verifyEmailUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerUserUseCase.execute(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUserUseCase.execute(request));
    }

    @GetMapping(value = "/verify", produces = "text/html")
    public ResponseEntity<String> verify(@RequestParam String token) {
        verifyEmailUseCase.execute(token);
        return ResponseEntity.ok("""
            <html>
            <body style="margin:0;background:#0f0f1a;font-family:'Segoe UI',Arial,sans-serif;
                         display:flex;align-items:center;justify-content:center;min-height:100vh;">
              <div style="text-align:center;background:rgba(255,255,255,0.05);border:1px solid rgba(255,255,255,0.1);
                          border-radius:16px;padding:48px 40px;max-width:400px;">
                <div style="font-size:48px;margin-bottom:16px;">✅</div>
                <h1 style="color:#fff;margin:0 0 12px;">Email Verified!</h1>
                <p style="color:rgba(255,255,255,0.6);margin:0 0 28px;">
                  Your account is now active. You can log in to TicketHub.
                </p>
                <a href="http://localhost:5173/login"
                   style="display:inline-block;background:linear-gradient(135deg,#7c3aed,#db2777);
                          color:#fff;text-decoration:none;font-weight:700;padding:14px 32px;border-radius:10px;">
                  Go to Login
                </a>
              </div>
            </body>
            </html>
            """);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "auth-service"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
