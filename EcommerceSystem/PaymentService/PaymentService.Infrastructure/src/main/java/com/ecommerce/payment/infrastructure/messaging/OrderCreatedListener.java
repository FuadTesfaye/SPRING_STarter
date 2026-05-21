package com.ecommerce.payment.infrastructure.messaging;

import com.ecommerce.payment.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @RabbitListener(queues = "payment.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("PAYMENT: Received OrderCreatedEvent for order {}", event.getOrderId());
        
        // Delegate to Application layer
        processPaymentUseCase.process(event.getOrderId(), event.getTotalAmount());
    }
}
