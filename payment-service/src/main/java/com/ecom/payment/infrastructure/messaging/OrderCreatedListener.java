package com.ecom.payment.infrastructure.messaging;

import com.ecom.payment.application.dto.OrderCreatedEvent;
import com.ecom.payment.application.service.PaymentProcessor;

public class OrderCreatedListener {
    private final PaymentProcessor paymentProcessor;

    public OrderCreatedListener(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentProcessor.processPayment(event);
    }
}
