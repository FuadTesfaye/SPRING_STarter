package com.example.payment.domain.port;

import com.example.payment.domain.model.Payment;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
}
