package com.ecommerce.payment.infrastructure.persistence.adapter;

import com.ecommerce.payment.domain.entity.Payment;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import com.ecommerce.payment.infrastructure.persistence.entity.PaymentEntity;
import com.ecommerce.payment.infrastructure.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId).map(this::toDomain);
    }

    private PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus() != null ? payment.getStatus().name() : null)
                .timestamp(payment.getTimestamp())
                .build();
    }

    private Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .amount(entity.getAmount())
                .status(entity.getStatus() != null ? Payment.PaymentStatus.valueOf(entity.getStatus()) : null)
                .timestamp(entity.getTimestamp())
                .build();
    }
}
