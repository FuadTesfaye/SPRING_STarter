package com.school.auth.domain.event;

import java.time.LocalDateTime;

// Domain event - plain Java, no framework dependencies
public class UserRegisteredEvent {

    private String userId;
    private String username;
    private String email;
    private LocalDateTime occurredAt;

    public UserRegisteredEvent() {}

    public UserRegisteredEvent(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.occurredAt = LocalDateTime.now();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
