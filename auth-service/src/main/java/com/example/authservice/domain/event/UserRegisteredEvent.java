package com.example.authservice.domain.event;
import java.time.Instant;
import java.util.UUID;
public class UserRegisteredEvent {
    private UUID userId;
    private String email;
    private Instant occurredAt;
    public UserRegisteredEvent() {}
    public UserRegisteredEvent(UUID userId, String email, Instant occurredAt) {
        this.userId = userId; this.email = email; this.occurredAt = occurredAt;
    }
    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setUserId(UUID u) { this.userId = u; }
    public void setEmail(String e) { this.email = e; }
    public void setOccurredAt(Instant t) { this.occurredAt = t; }
}
