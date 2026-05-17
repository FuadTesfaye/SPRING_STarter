package com.example.paymentservice.domain.interfaces;

import com.example.paymentservice.domain.entities.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
