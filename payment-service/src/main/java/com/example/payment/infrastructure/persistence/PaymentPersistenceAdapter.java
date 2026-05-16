package com.example.payment.infrastructure.persistence;

import com.example.payment.application.port.out.PaymentRepository;
import com.example.payment.domain.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository repository;

    public PaymentPersistenceAdapter(SpringDataPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId());
        entity.setOrderId(payment.getOrderId());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());
        entity.setReference(payment.getReference());
        entity.setFailureReason(payment.getFailureReason());
        entity.setProcessedAt(payment.getProcessedAt());

        return toDomain(repository.save(entity));
    }

    private Payment toDomain(PaymentEntity entity) {
        return new Payment(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getReference(),
                entity.getFailureReason(),
                entity.getProcessedAt()
        );
    }
}
