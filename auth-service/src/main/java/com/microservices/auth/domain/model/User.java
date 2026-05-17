package com.microservices.auth.domain.model;

import java.time.LocalDateTime;

/**
 * DOMAIN ENTITY: User (Aggregate Root)
 * 
 * WHAT: The main business object for authentication
 * WHY: Central entity that manages user identity
 * HOW: Coordinates value objects and enforces business rules
 * 
 * AGGREGATE ROOT: All operations on User profile go through User
 */
public class User {
    
    private UserId id;
    private Email email;
    private Username username;
    private Password password;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    
    // Private constructor - use factory method
    private User() {
    }
    
    /**
     * Factory method: Create a new registered user
     * Ensures all required fields are provided
     */
    public static User register(Email email, Username username, Password password) {
        User user = new User();
        user.id = UserId.generate();
        user.email = email;
        user.username = username;
        user.password = password;
        user.active = true;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }
    
    /**
     * Factory method: Reconstruct user from database
     */
    public static User reconstruct(
            UserId id,
            Email email,
            Username username,
            Password password,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        
        User user = new User();
        user.id = id;
        user.email = email;
        user.username = username;
        user.password = password;
        user.active = active;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }
    
    // Business methods
    
    /**
     * Record successful login
     */
    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
    
    /**
     * Deactivate user account
     */
    public void deactivate() {
        if (!this.active) {
            throw new IllegalStateException("User is already deactivated");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Activate user account
     */
    public void activate() {
        if (this.active) {
            throw new IllegalStateException("User is already active");
        }
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Change password with verification of old password
     */
    public void changePassword(String oldPassword, Password newPassword) {
        if (!this.password.matches(oldPassword)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verify login password
     */
    public boolean verifyPassword(String rawPassword) {
        return this.password.matches(rawPassword);
    }
    
    /**
     * Check if user can login
     */
    public boolean canLogin() {
        return this.active;
    }
    
    // Getters
    
    public UserId getId() {
        return id;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public Username getUsername() {
        return username;
    }
    
    public Password getPassword() {
        return password;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
}
