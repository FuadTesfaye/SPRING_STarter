package com.company.auth.domain.event;

import java.time.LocalDateTime;

public class UserRegisteredEvent {
    private String username;
    private String email;
    private LocalDateTime registeredAt;

    public UserRegisteredEvent(String username, String email) {
        this.username = username;
        this.email = email;
        this.registeredAt = LocalDateTime.now();
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
}
