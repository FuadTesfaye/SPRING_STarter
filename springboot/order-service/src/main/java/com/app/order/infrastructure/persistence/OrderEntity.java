package com.app.order.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;
    private String customerId;
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    private OrderStatusEntity status;

    public OrderEntity() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatusEntity getStatus() { return status; }
    public void setStatus(OrderStatusEntity status) { this.status = status; }
}

enum OrderStatusEntity {
    CREATED, PAID, CANCELLED, SHIPPED
}
