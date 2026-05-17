package com.example.shipping.domain.event;

import java.util.UUID;

public class PaymentCompletedEvent {
    private UUID orderId;
    private UUID userId;
    private String eventType;

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}