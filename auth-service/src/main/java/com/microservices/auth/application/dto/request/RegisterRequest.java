package com.microservices.auth.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO: RegisterRequest
 * 
 * WHAT: Data needed to register a new user
 * WHY: 
 * - Validates input format BEFORE reaching domain
 * - Keeps validation annotations separate from domain
 * - Different from domain objects (uses String, not Email/Username)
 * 
 * VALIDATION ANNOTATIONS:
 * - @NotBlank: Spring validates this is not null/empty BEFORE controller method
 * - @Email: Spring validates email format
 * - @Size: Spring validates string length
 * 
 * WHY VALIDATE TWICE?
 * - This validation: Quick format checks (is it an email format?)
 * - Domain validation: Business rules (is this email already used?)
 */
public class RegisterRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    // Default constructor (needed for JSON deserialization)
    public RegisterRequest() {
    }
    
    // Constructor for easy creation
    public RegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
