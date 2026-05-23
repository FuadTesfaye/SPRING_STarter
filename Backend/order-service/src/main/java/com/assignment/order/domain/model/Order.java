package com.assignment.order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Order {
    private UUID id;
    private UUID userId;
    private String eventId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private String status;
    private Instant createdAt;

    public Order() {}

    public Order(UUID id, UUID userId, String eventId, String productName, int quantity,
                 BigDecimal price, BigDecimal totalAmount, String status, Instant createdAt) {
        this.id = id; this.userId = userId; this.eventId = eventId;
        this.productName = productName; this.quantity = quantity;
        this.price = price; this.totalAmount = totalAmount;
        this.status = status; this.createdAt = createdAt;
    }

    public static Order create(UUID userId, String eventId, String productName, int quantity, BigDecimal price) {
        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
        return new Order(UUID.randomUUID(), userId, eventId, productName, quantity,
            price, total, "PENDING", Instant.now());
    }

    public UUID getId()           { return id; }
    public UUID getUserId()       { return userId; }
    public String getEventId()    { return eventId; }
    public String getProductName(){ return productName; }
    public int getQuantity()      { return quantity; }
    public BigDecimal getPrice()  { return price; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus()     { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setStatus(String status) { this.status = status; }
}
