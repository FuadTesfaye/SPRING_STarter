package com.example.payment.application.ports;

public interface PaymentEventPublisher {
    void publishPaymentCompleted(Long orderId);
    void publishPaymentFailed(Long orderId, String reason);
}