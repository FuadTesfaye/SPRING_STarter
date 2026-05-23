package com.ticketbooking.auth.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String passwordHash;
    private String fullName;
    private String role;
    private Instant createdAt;
    private boolean verified;
    private String verificationToken;
    private boolean banned;

    public User() {}

    public User(UUID id, String email, String passwordHash, String fullName, String role,
                Instant createdAt, boolean verified, String verificationToken, boolean banned) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
        this.verified = verified;
        this.verificationToken = verificationToken;
        this.banned = banned;
    }

    public static User create(String email, String passwordHash, String fullName) {
        return new User(UUID.randomUUID(), email, passwordHash, fullName, "USER",
                Instant.now(), false, UUID.randomUUID().toString(), false);
    }

    public void verify() {
        this.verified = true;
        this.verificationToken = null;
    }

    public void ban()   { this.banned = true; }
    public void unban() { this.banned = false; }
    public void promoteToAdmin() { this.role = "ADMIN"; }
    public void demoteToUser()   { this.role = "USER"; }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isVerified() { return verified; }
    public String getVerificationToken() { return verificationToken; }
    public boolean isBanned() { return banned; }
}
