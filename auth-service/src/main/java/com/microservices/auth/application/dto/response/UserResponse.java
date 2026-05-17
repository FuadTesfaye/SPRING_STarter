package com.microservices.auth.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO: UserResponse
 * 
 * WHAT: User data sent to clients
 * WHY: 
 * - Clean representation without sensitive data
 * - No password (security)
 * - Formatted for API consumers
 */
public class UserResponse {
    
    private String id;
    private String email;
    private String username;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    public UserResponse() {
    }
    
    public UserResponse(String id, String email, String username, boolean active, 
                       LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.active = active;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
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
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
    
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
