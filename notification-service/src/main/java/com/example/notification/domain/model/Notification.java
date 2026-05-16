package com.example.notification.domain.model;

import java.time.Instant;

public class Notification {

    private Long id;
    private String eventType;
    private String message;
    private String details;
    private Instant createdAt;

    public Notification() {
    }

    public Notification(Long id, String eventType, String message, String details, Instant createdAt) {
        this.id = id;
        this.eventType = eventType;
        this.message = message;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
