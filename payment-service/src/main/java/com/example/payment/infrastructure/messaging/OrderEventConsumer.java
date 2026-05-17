package com.example.payment.infrastructure.messaging;

import com.example.payment.application.service.PaymentService;
import com.example.payment.domain.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    public OrderEventConsumer(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "payment.order.queue")
    public void handleOrderCreated(String message) {
        try {
            log.info("Received order created event");
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
            paymentService.processPayment(event);
        } catch (Exception e) {
            log.error("Error processing order event: {}", e.getMessage());
        }
    }
}