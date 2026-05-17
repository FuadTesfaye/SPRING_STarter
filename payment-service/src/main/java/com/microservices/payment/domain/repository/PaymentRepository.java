package com.microservices.payment.domain.repository;

import com.microservices.payment.domain.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(String id);
    List<Payment> findByOrderId(String orderId);
    List<Payment> findAll();
}
