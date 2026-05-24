package com.ecom.payment.domain.repository;

import com.ecom.payment.domain.model.Payment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    List<Payment> findAll();
    Optional<Payment> findByOrderId(UUID orderId);
}
