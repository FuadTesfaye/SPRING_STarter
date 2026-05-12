package com.microservices.paymentservice.domain.port;

import com.microservices.paymentservice.domain.model.Payment;

public interface PaymentRepositoryPort {

    Payment save(Payment payment);
}