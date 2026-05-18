package com.app.payment.infrastructure.persistence;

import com.app.payment.domain.Payment;
import com.app.payment.domain.PaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final SpringDataPaymentRepository repository;

    public PaymentRepositoryImpl(SpringDataPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getStatus());
        PaymentEntity saved = repository.save(entity);
        return new Payment(saved.getId(), saved.getOrderId(), saved.getAmount(), saved.getStatus());
    }
}
