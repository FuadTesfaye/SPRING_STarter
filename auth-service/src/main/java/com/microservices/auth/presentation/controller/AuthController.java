package com.microservices.auth.presentation.controller;

import com.microservices.auth.application.dto.request.LoginRequest;
import com.microservices.auth.application.dto.request.RegisterRequest;
import com.microservices.auth.application.dto.response.AuthResponse;
import com.microservices.auth.application.dto.response.UserResponse;
import com.microservices.auth.application.service.AuthApplicationService;
import com.microservices.auth.domain.exception.DomainException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AUTH CONTROLLER
 * 
 * WHAT: REST API endpoints for authentication
 * WHY:
 * - Entry point for HTTP requests
 * - Translates HTTP to application calls
 * - Returns HTTP responses
 * 
 * ENDPOINTS:
 * - POST /api/auth/register - Create new account
 * - POST /api/auth/login - Login to account
 * - GET /api/auth/users/{id} - Get user profile
 * - GET /api/auth/validate - Validate JWT token
 * 
 * DESIGN PRINCIPLES:
 * - Thin controller (no business logic)
 * - Delegates everything to application service
 * - Only handles HTTP concerns (status codes, headers)
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API endpoints")
public class AuthController {
    
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthApplicationService authApplicationService;
    
    // Constructor injection
    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }
    
    /**
     * REGISTER ENDPOINT
     * 
     * POST /api/auth/register
     * 
     * Request Body:
     * {
     *   "email": "john@email.com",
     *   "username": "john_doe",
     *   "password": "password123"
     * }
     * 
     * Success Response (201 Created):
     * {
     *   "userId": "a1b2c3...",
     *   "username": "john_doe",
     *   "email": "john@email.com",
     *   "message": "User registered successfully"
     * }
     * 
     * Error Response (409 Conflict):
     * {
     *   "status": 409,
     *   "message": "User already exists with email: john@email.com",
     *   "errors": {"email": "already taken"}
     * }
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Email or username already exists")
    })
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        
        log.info("REST request: POST /api/auth/register - email: {}", request.getEmail());
        
        AuthResponse response = authApplicationService.register(request);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)  // 201 Created
            .body(response);
    }
    
    /**
     * LOGIN ENDPOINT
     * 
     * POST /api/auth/login
     * 
     * Request Body:
     * {
     *   "email": "john@email.com",
     *   "password": "password123"
     * }
     * 
     * Success Response (200 OK):
     * {
     *   "token": "eyJhbGciOi...",
     *   "userId": "a1b2c3...",
     *   "username": "john_doe",
     *   "message": "Login successful"
     * }
     * 
     * Error Response (401 Unauthorized):
     * {
     *   "status": 401,
     *   "message": "Invalid email or password"
     * }
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        
        log.info("REST request: POST /api/auth/login - email: {}", request.getEmail());
        
        AuthResponse response = authApplicationService.login(request);
        
        return ResponseEntity.ok(response);  // 200 OK
    }
    
    /**
     * GET USER BY ID
     * 
     * GET /api/auth/users/{id}
     * 
     * Success Response (200 OK):
     * {
     *   "id": "a1b2c3...",
     *   "email": "john@email.com",
     *   "username": "john_doe",
     *   "active": true,
     *   "createdAt": "2026-05-11T12:00:00"
     * }
     */
    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        
        log.info("REST request: GET /api/auth/users/{}", id);
        
        UserResponse response = authApplicationService.getUserById(id);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * HEALTH CHECK
     * 
     * GET /api/auth/health
     * 
     * Simple endpoint to check if service is running
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if service is running")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth Service is running!");
    }
}
