package com.example.paymentservice.domain.repository;

import com.example.paymentservice.domain.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Payment save(Payment payment);

    boolean existsByOrderId(UUID orderId);

    Optional<Payment> findById(UUID id);
}