package com.microservices.orderservice.application.event;

import java.io.Serializable;
import java.time.Instant;

public class UserRegisteredEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String eventType;
    private Long timestamp;

    public UserRegisteredEvent() {}

    public UserRegisteredEvent(String username, String eventType, Long timestamp) {
        this.username = username;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "username='" + username + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}