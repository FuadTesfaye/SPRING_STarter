package com.microservices.paymentservice.infrastructure.persistence;

import com.microservices.paymentservice.domain.model.Payment;
import com.microservices.paymentservice.domain.port.PaymentRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryAdapter
        implements PaymentRepositoryPort {

    private final PaymentJpaRepository repository;

    public PaymentRepositoryAdapter(
            PaymentJpaRepository repository) {

        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {

        PaymentEntity entity =
                new PaymentEntity();

        entity.setUsername(payment.getUsername());
        entity.setStatus(payment.getStatus());

        repository.save(entity);

        return payment;
    }
}