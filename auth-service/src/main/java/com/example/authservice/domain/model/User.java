package com.example.authservice.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String passwordHash;
    private Instant createdAt;

    public User() {}
    public User(UUID id, String email, String passwordHash, Instant createdAt) {
        this.id = id; this.email = email; this.passwordHash = passwordHash; this.createdAt = createdAt;
    }
    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Instant getCreatedAt() { return createdAt; }
    public void setId(UUID id) { this.id = id; }
    public void setEmail(String e) { this.email = e; }
    public void setPasswordHash(String p) { this.passwordHash = p; }
    public void setCreatedAt(Instant t) { this.createdAt = t; }
}
