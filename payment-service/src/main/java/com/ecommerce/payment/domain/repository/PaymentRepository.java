package com.ecommerce.payment.domain.repository;

import com.ecommerce.payment.domain.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(String id);
    Optional<Payment> findByOrderId(String orderId);
    List<Payment> findAll();
}
