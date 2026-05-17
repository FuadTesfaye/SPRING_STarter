package com.eventdriven.authservice.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRegisteredEvent implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private LocalDateTime timestamp;

    public UserRegisteredEvent() {}

    public UserRegisteredEvent(Long userId, String username, String email, String fullName, LocalDateTime timestamp) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.timestamp = timestamp;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private String username;
        private String email;
        private String fullName;
        private LocalDateTime timestamp;

        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public UserRegisteredEvent build() {
            return new UserRegisteredEvent(userId, username, email, fullName, timestamp);
        }
    }
}