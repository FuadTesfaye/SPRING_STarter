package com.example.paymentservice.infrastructure.persistence.adapter;

import com.example.paymentservice.domain.entities.Payment;
import com.example.paymentservice.domain.enums.PaymentStatus;
import com.example.paymentservice.infrastructure.persistence.entity.PaymentJpaEntity;

public class PaymentPersistenceMapper {

    public PaymentJpaEntity toEntity(Payment payment) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        entity.setPaymentId(payment.paymentId());
        entity.setOrderId(payment.orderId());
        entity.setProductId(payment.productId());
        entity.setQuantity(payment.quantity());
        entity.setAmount(payment.amount());
        entity.setStatus(payment.status().name());
        return entity;
    }

    public Payment toDomain(PaymentJpaEntity entity) {
        return new Payment(
                entity.getPaymentId(),
                entity.getOrderId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getAmount(),
                PaymentStatus.valueOf(entity.getStatus())
        );
    }
}
