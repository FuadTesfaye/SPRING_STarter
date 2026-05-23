package com.assignment.payment.domain.repository;

import com.assignment.payment.domain.model.Payment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
    List<Payment> findByOrderId(UUID orderId);
}
