package com.example.payment.infrastructure.messaging;

import com.example.payment.application.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private final PaymentService paymentService;

    public PaymentEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(String username) {
        System.out.println("Payment-service received order.created for: " + username);
        paymentService.processPayment(username);
    }
}
