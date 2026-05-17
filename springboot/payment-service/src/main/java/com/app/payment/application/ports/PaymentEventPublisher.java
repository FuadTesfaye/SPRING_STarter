package com.app.payment.application.ports;

import com.app.payment.domain.Payment;

public interface PaymentEventPublisher {
    void publishPaymentCompleted(Payment payment);
    void publishPaymentFailed(Payment payment);
}
