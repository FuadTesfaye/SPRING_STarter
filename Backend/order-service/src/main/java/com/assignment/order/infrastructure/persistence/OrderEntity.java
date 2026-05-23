package com.assignment.order.infrastructure.persistence;

import com.assignment.order.domain.model.Order;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    public String id;

    @Column(nullable = false)
    public String userId;

    @Column(nullable = false)
    public String eventId;

    @Column(nullable = false)
    public String productName;

    @Column(nullable = false)
    public int quantity;

    @Column(nullable = false)
    public BigDecimal price;

    @Column(nullable = false)
    public BigDecimal totalAmount;

    @Column(nullable = false)
    public String status;

    public Instant createdAt;

    public static OrderEntity fromDomain(Order o) {
        OrderEntity e = new OrderEntity();
        e.id = o.getId().toString();
        e.userId = o.getUserId().toString();
        e.eventId = o.getEventId();
        e.productName = o.getProductName();
        e.quantity = o.getQuantity();
        e.price = o.getPrice();
        e.totalAmount = o.getTotalAmount();
        e.status = o.getStatus();
        e.createdAt = o.getCreatedAt();
        return e;
    }

    public Order toDomain() {
        return new Order(UUID.fromString(id), UUID.fromString(userId), eventId, productName,
            quantity, price, totalAmount, status, createdAt);
    }
}
