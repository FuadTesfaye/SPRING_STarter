package com.example.auth.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRegisteredEvent {
    private final UUID userId;
    private final String username;
    private final String email;
    private final LocalDateTime timestamp;

    public UserRegisteredEvent(UUID userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getTimestamp() { return timestamp; }
}