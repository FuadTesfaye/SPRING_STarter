package com.microservices.paymentservice.infrastructure.messaging;

import com.microservices.paymentservice.application.event.OrderCreatedEvent;
import com.microservices.paymentservice.application.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private final PaymentService paymentService;

    public PaymentEventListener(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @RabbitListener(
            queues = "order.created.queue")
    public void handle(
            OrderCreatedEvent event) {

        paymentService.processPayment(
                event.getUsername());
    }
}