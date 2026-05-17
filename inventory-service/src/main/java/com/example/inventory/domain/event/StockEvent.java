package com.example.inventory.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockEvent {
    private UUID orderId;
    private UUID userId;
    private UUID productId;
    private int quantity;
    private String eventType; // "StockReserved" or "StockFailed"
    private LocalDateTime timestamp;

    public StockEvent(UUID orderId, UUID userId, UUID productId, int quantity, String eventType) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public UUID getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getEventType() { return eventType; }
    public LocalDateTime getTimestamp() { return timestamp; }
}