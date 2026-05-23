package com.example.paymentservice.infrastructure.persistence;
import com.example.paymentservice.application.port.PaymentRepositoryPort;
import com.example.paymentservice.domain.model.Payment;
import org.springframework.stereotype.Repository;
@Repository
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {
    private final PaymentJpaRepository jpa;
    public PaymentRepositoryAdapter(PaymentJpaRepository j) { jpa=j; }
    @Override public Payment save(Payment p) {
        PaymentEntity e = new PaymentEntity();
        e.setId(p.getId()); e.setOrderId(p.getOrderId()); e.setAmount(p.getAmount());
        e.setStatus(p.getStatus()); e.setProcessedAt(p.getProcessedAt());
        jpa.save(e); return p;
    }
}
