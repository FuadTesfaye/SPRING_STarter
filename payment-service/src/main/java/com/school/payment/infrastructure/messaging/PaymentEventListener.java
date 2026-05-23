package com.school.payment.infrastructure.messaging;

import com.school.payment.application.service.PaymentService;
import com.school.payment.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handleOrderCreated(OrderCreatedEventDto event) {
        log.info("[PAYMENT] Received order.created event for order: {}", event.getOrderId());
        paymentService.processPayment(event.getOrderId(), event.getStudentId(), event.getAmount());
    }
}
