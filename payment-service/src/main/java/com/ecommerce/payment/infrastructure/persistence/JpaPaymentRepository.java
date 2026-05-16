package com.ecommerce.payment.infrastructure.persistence;

import com.ecommerce.payment.domain.model.Payment;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaPaymentRepository implements PaymentRepository {
    private final SpringDataPaymentRepository repo;

    @Override public Payment save(Payment p) { return repo.save(p); }
    @Override public Optional<Payment> findById(String id) { return repo.findById(id); }
    @Override public Optional<Payment> findByOrderId(String oid) { return repo.findByOrderId(oid); }
    @Override public List<Payment> findAll() { return repo.findAll(); }
}
