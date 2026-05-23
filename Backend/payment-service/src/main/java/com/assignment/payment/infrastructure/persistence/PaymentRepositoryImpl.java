package com.assignment.payment.infrastructure.persistence;

import com.assignment.payment.domain.model.Payment;
import com.assignment.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpa;

    public PaymentRepositoryImpl(JpaPaymentRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Payment save(Payment payment) {
        return jpa.save(PaymentEntity.fromDomain(payment)).toDomain();
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpa.findById(id.toString()).map(PaymentEntity::toDomain);
    }

    @Override
    public List<Payment> findByOrderId(UUID orderId) {
        return jpa.findByOrderId(orderId.toString()).stream().map(PaymentEntity::toDomain).toList();
    }
}
