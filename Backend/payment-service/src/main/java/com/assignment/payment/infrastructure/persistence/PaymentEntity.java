package com.assignment.payment.infrastructure.persistence;

import com.assignment.payment.domain.model.Payment;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    public String id;

    @Column(nullable = false)
    public String orderId;

    @Column(nullable = false)
    public String userId;

    @Column(nullable = false)
    public BigDecimal amount;

    @Column(nullable = false)
    public String status;

    public Instant createdAt;

    public static PaymentEntity fromDomain(Payment p) {
        PaymentEntity e = new PaymentEntity();
        e.id = p.getId().toString();
        e.orderId = p.getOrderId().toString();
        e.userId = p.getUserId().toString();
        e.amount = p.getAmount();
        e.status = p.getStatus();
        e.createdAt = p.getCreatedAt();
        return e;
    }

    public Payment toDomain() {
        return new Payment(UUID.fromString(id), UUID.fromString(orderId), UUID.fromString(userId), amount, status, createdAt);
    }
}
