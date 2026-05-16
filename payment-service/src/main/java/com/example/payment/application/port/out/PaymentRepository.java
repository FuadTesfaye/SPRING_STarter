package com.example.payment.application.port.out;

import com.example.payment.domain.model.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
