package com.ecom.payment.infrastructure.persistence;

import com.ecom.payment.domain.model.Payment;
import com.ecom.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class PostgresPaymentRepositoryAdapter implements PaymentRepository {
    private final JpaPaymentRepository repository;

    public PostgresPaymentRepositoryAdapter(JpaPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getProcessedAt()
        );
        PaymentEntity saved = repository.save(entity);
        return new Payment(saved.getId(), saved.getOrderId(), saved.getAmount(), saved.getStatus(), saved.getProcessedAt());
    }

    @Override
    public java.util.List<Payment> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Payment(entity.getId(), entity.getOrderId(), entity.getAmount(), entity.getStatus(), entity.getProcessedAt()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public java.util.Optional<Payment> findByOrderId(java.util.UUID orderId) {
        return repository.findByOrderId(orderId)
                .map(entity -> new Payment(entity.getId(), entity.getOrderId(), entity.getAmount(), entity.getStatus(), entity.getProcessedAt()));
    }
}
