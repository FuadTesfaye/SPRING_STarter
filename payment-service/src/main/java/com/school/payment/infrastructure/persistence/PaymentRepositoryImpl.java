package com.school.payment.infrastructure.persistence;

import com.school.payment.application.port.PaymentRepository;
import com.school.payment.domain.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = toEntity(payment);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private PaymentJpaEntity toEntity(Payment p) {
        PaymentJpaEntity e = new PaymentJpaEntity();
        e.setId(p.getId());
        e.setOrderId(p.getOrderId());
        e.setStudentId(p.getStudentId());
        e.setAmount(p.getAmount());
        e.setStatus(p.getStatus());
        e.setReference(p.getReference());
        e.setProcessedAt(p.getProcessedAt());
        return e;
    }

    private Payment toDomain(PaymentJpaEntity e) {
        Payment p = new Payment();
        p.setId(e.getId());
        p.setOrderId(e.getOrderId());
        p.setStudentId(e.getStudentId());
        p.setAmount(e.getAmount());
        p.setStatus(e.getStatus());
        p.setReference(e.getReference());
        p.setProcessedAt(e.getProcessedAt());
        return p;
    }
}
