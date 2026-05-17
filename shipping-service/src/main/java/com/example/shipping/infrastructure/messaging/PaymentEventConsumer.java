package com.example.shipping.infrastructure.messaging;

import com.example.shipping.application.service.ShippingService;
import com.example.shipping.domain.event.PaymentCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);
    private final ShippingService shippingService;
    private final ObjectMapper objectMapper;

    public PaymentEventConsumer(ShippingService shippingService, ObjectMapper objectMapper) {
        this.shippingService = shippingService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "shipping.payment.queue")
    public void handlePaymentCompleted(String message) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(message, PaymentCompletedEvent.class);
            shippingService.handlePaymentCompleted(event.getOrderId(), event.getUserId());
        } catch (Exception e) {
            log.error("Error processing payment event: {}", e.getMessage());
        }
    }
}