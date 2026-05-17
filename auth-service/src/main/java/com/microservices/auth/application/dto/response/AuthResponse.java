package com.microservices.auth.application.dto.response;

/**
 * DTO: AuthResponse
 * 
 * WHAT: Response after successful login/registration
 * WHY: 
 * - Contains JWT token (what client needs)
 * - Doesn't expose password or internal details
 * - Clean API response format
 */
public class AuthResponse {
    
    private String token;
    private String userId;
    private String username;
    private String email;
    private String message;
    
    public AuthResponse() {
    }
    
    public AuthResponse(String token, String userId, String username, String email, String message) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.message = message;
    }
    
    // Factory methods for different scenarios
    
    public static AuthResponse forRegistration(String userId, String username, String email) {
        AuthResponse response = new AuthResponse();
        response.userId = userId;
        response.username = username;
        response.email = email;
        response.message = "User registered successfully";
        return response;
    }
    
    public static AuthResponse forLogin(String token, String userId, String username, String email) {
        AuthResponse response = new AuthResponse();
        response.token = token;
        response.userId = userId;
        response.username = username;
        response.email = email;
        response.message = "Login successful";
        return response;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
