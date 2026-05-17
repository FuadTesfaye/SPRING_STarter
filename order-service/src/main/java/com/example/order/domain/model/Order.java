package com.example.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private UUID userId;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItem> items;
    private LocalDateTime createdAt;

    private Order(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.userId = builder.userId;
        this.status = "PENDING";
        this.totalAmount = calculateTotal(builder.items);
        this.items = new ArrayList<>(builder.items);
        this.createdAt = LocalDateTime.now();
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static class Builder {
        private UUID id;
        private UUID userId;
        private List<OrderItem> items;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder userId(UUID userId) { this.userId = userId; return this; }
        public Builder items(List<OrderItem> items) { this.items = items; return this; }

        public Order build() {
            if (userId == null) throw new IllegalStateException("User ID is required");
            if (items == null || items.isEmpty()) throw new IllegalStateException("Order must have at least one item");
            return new Order(this);
        }
    }
}