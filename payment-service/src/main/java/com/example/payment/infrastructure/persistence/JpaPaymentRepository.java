package com.example.payment.infrastructure.persistence;

import com.example.payment.domain.model.Payment;
import com.example.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaPaymentRepository implements PaymentRepository {
    private final SpringDataPaymentRepository springDataPaymentRepository;

    public JpaPaymentRepository(SpringDataPaymentRepository springDataPaymentRepository) {
        this.springDataPaymentRepository = springDataPaymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntity.fromDomain(payment);
        entity = springDataPaymentRepository.save(entity);
        return entity.toDomain();
    }
}