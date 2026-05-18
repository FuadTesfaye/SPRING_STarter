package com.example.payment.infrastructure.persistence;

import com.example.payment.domain.model.Payment;
import com.example.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository // This tells Spring: "I am the PaymentRepository you are looking for!"
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository repository;

    @Override
    public Payment save(Payment payment) {
        // Map Domain model to Entity
        PaymentEntity entity = new PaymentEntity(null, payment.getOrderId(),
                payment.getAmount(), payment.getStatus());
        PaymentEntity saved = repository.save(entity);

        // Map Entity back to Domain model
        return new Payment(saved.getId(), saved.getOrderId(),
                saved.getAmount(), saved.getStatus());
    }
}