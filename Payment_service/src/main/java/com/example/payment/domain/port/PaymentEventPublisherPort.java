package com.example.payment.domain.port;

public interface PaymentEventPublisherPort {

    void publishCompleted(String orderId, double amount);

    void publishFailed(String orderId, String reason);
}
