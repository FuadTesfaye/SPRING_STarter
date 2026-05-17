package com.example.payment.domain.repository;

import com.example.payment.domain.model.Payment;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
}