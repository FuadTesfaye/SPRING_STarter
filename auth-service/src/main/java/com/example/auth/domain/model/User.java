package com.example.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.username = builder.username;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public static class Builder {
        private UUID id;
        private String username;
        private String email;
        private String passwordHash;
        private LocalDateTime createdAt;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public User build() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalStateException("Username is required");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalStateException("Email is required");
            }
            if (passwordHash == null || passwordHash.trim().isEmpty()) {
                throw new IllegalStateException("Password hash is required");
            }
            return new User(this);
        }
    }
}