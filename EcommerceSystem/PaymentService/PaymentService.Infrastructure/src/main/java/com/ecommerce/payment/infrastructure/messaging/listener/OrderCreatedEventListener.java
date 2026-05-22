package com.ecommerce.payment.infrastructure.messaging.listener;

import com.ecommerce.payment.application.service.PaymentApplicationService;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventListener {

    private final PaymentApplicationService paymentService;

    @RabbitListener(queues = "payment.order_created.queue")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);
        paymentService.processPayment(event.getOrderId(), event.getUserId(), event.getTotalAmount());
    }
}
