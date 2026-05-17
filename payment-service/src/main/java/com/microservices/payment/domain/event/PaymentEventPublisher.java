package com.microservices.payment.domain.event;

public interface PaymentEventPublisher {
    void publishPaymentCompleted(PaymentCompletedEvent event);
    void publishPaymentFailed(PaymentFailedEvent event);
}
