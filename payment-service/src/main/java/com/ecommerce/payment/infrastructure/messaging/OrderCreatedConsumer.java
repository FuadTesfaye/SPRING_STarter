package com.ecommerce.payment.infrastructure.messaging;

import com.ecommerce.payment.application.service.PaymentService;
import com.ecommerce.payment.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = "payment.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("[PAYMENT] Received order.created event for order: {}", event.getOrderId());
        paymentService.processPayment(event);
    }
}
