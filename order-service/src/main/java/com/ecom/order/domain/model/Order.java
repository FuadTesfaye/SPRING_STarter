package com.ecom.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final String status;
    private final LocalDateTime createdAt;

    public Order(UUID id, UUID customerId, List<OrderItem> items, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.items = List.copyOf(items);
        this.totalAmount = calculateTotal(items);
        this.status = status;
        this.createdAt = createdAt;
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void validate() {
        if (customerId == null) throw new IllegalArgumentException("Customer ID is required");
        if (items.isEmpty()) throw new IllegalArgumentException("Order must have at least one item");
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
