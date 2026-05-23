package com.ecom.notification.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final UUID id;
    private final String eventType;
    private final String payload;
    private final LocalDateTime receivedAt;

    public Notification(UUID id, String eventType, String payload, LocalDateTime receivedAt) {
        this.id = id;
        this.eventType = eventType;
        this.payload = payload;
        this.receivedAt = receivedAt;
    }

    public UUID getId() { return id; }
    public String getEventType() { return eventType; }
    public String getPayload() { return payload; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
}
