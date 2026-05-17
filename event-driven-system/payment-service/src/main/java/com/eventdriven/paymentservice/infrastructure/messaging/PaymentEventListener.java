package com.eventdriven.paymentservice.infrastructure.messaging;

import com.eventdriven.paymentservice.application.dto.OrderCreatedEvent;
import com.eventdriven.paymentservice.application.service.PaymentApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);
    private final PaymentApplicationService paymentApplicationService;
    
    public PaymentEventListener(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }
    
    @RabbitListener(queues = "payment.order.created.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreated event: {}", event);
        paymentApplicationService.processPayment(event);
    }
}