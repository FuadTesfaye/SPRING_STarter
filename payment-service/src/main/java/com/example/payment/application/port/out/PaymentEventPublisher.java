package com.example.payment.application.port.out;

import com.example.payment.domain.model.Payment;

public interface PaymentEventPublisher {

    void publishPaymentCompleted(Payment payment);

    void publishPaymentFailed(Payment payment);
}
