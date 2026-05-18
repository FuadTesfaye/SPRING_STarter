package com.company.payment.infrastructure.persistence.repository;

import com.company.payment.domain.model.Payment;
import com.company.payment.domain.repository.IPaymentRepository;
import com.company.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceAdapter implements IPaymentRepository {
    private final SpringDataPaymentRepository repository;

    public PaymentPersistenceAdapter(SpringDataPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntity.fromDomain(payment);
        PaymentEntity saved = repository.save(entity);
        return saved.toDomain();
    }
}
