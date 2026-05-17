package com.example.paymentservice.infrastructure.messaging;


import com.example.paymentservice.application.dto.OrderCreatedEvent;
import com.example.paymentservice.application.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final PaymentService paymentService;

    public OrderCreatedListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {

        try {
            System.out.println("📩 Received order.created: " + event.getOrderId());

            paymentService.processPayment(event);

        } catch (Exception e) {
            System.err.println("❌ Failed processing message: " + e.getMessage());

            // IMPORTANT: prevents endless retry loop
            // message will be ACKed instead of requeued
        }
    }
}
