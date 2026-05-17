package com.microservices.payment.infrastructure.persistence.mapper;

import com.microservices.payment.domain.model.Payment;
import com.microservices.payment.domain.model.PaymentStatus;
import com.microservices.payment.infrastructure.persistence.entity.PaymentJpaEntity;

public final class PaymentPersistenceMapper {
    
    private PaymentPersistenceMapper() {}
    
    public static PaymentJpaEntity toJpaEntity(Payment payment) {
        if (payment == null) return null;
        PaymentJpaEntity entity = new PaymentJpaEntity();
        entity.setId(payment.getId());
        entity.setOrderId(payment.getOrderId());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus().name());
        entity.setReason(payment.getReason());
        entity.setProcessedAt(payment.getProcessedAt());
        return entity;
    }
    
    public static Payment toDomain(PaymentJpaEntity entity) {
        if (entity == null) return null;
        return Payment.reconstruct(
            entity.getId(),
            entity.getOrderId(),
            entity.getAmount(),
            PaymentStatus.valueOf(entity.getStatus()),
            entity.getReason(),
            entity.getProcessedAt()
        );
    }
}
