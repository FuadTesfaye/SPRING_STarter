package com.ticketbooking.notification.domain.model;

import java.time.Instant;

/**
 * Pure domain object — no framework annotations.
 * Represents a notification event to be logged/sent.
 */
public class NotificationMessage {

    private final String routingKey;
    private final String message;
    private final Instant receivedAt;

    public NotificationMessage(String routingKey, String message, Instant receivedAt) {
        this.routingKey = routingKey;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public static NotificationMessage of(String routingKey, String message) {
        return new NotificationMessage(routingKey, message, Instant.now());
    }

    public String getRoutingKey() { return routingKey; }
    public String getMessage()    { return message; }
    public Instant getReceivedAt() { return receivedAt; }
}
