package com.microservices.payment.infrastructure.messaging;

import com.microservices.payment.application.service.PaymentApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RABBITMQ EVENT LISTENER
 * 
 * WHAT: Listens for order.created events and triggers payment processing
 * WHY: Event-driven - reacts to orders automatically
 * 
 * FLOW:
 * 1. Order Service publishes order.created
 * 2. This listener receives it
 * 3. Calls Application Service (which calls Domain Service)
 * 4. Domain processes payment and publishes result event
 */
@Component
public class PaymentEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);
    
    private final PaymentApplicationService paymentApplicationService;
    
    public PaymentEventListener(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }
    
    /**
     * Listen for order.created events
     * When an order is created, process payment for it
     */
    @RabbitListener(queues = "${app.rabbitmq.queue.order-created}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("========================================");
        log.info("💰 Payment Service: Received order.created event");
        log.info("   Order ID: {}", event.getOrderId());
        log.info("   Amount: {}", event.getTotalAmount());
        log.info("   User ID: {}", event.getUserId());
        log.info("========================================");
        
        // Call Application Service (which calls Domain Service)
        paymentApplicationService.processPayment(
            event.getOrderId(),
            event.getTotalAmount()
        );
    }
}
