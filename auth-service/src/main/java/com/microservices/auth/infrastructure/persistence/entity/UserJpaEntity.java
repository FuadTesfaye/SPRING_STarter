package com.microservices.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA ENTITY: UserJpaEntity
 * 
 * WHAT: Database representation of a User
 * WHY: 
 * - Domain User is pure business (no JPA annotations)
 * - This maps domain to database table
 * - Keeps JPA dependencies in infrastructure layer
 * 
 * MAPPING:
 * - This class → "users" table in database
 * - Each field → Column in the table
 * 
 * WHY SEPARATE FROM DOMAIN USER:
 * - Domain shouldn't care about database
 * - Can change database schema without touching business logic
 * - Different concerns: persistence vs business rules
 */
@Entity
@Table(name = "users")
public class UserJpaEntity {
    
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;  // Already hashed by domain
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    // Default constructor (required by JPA)
    public UserJpaEntity() {
    }
    
    // Constructor for creating new entities
    public UserJpaEntity(UUID id, String email, String username, String password, 
                        boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters (JPA needs these)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}
