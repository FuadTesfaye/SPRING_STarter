package com.assignment.payment.infrastructure.messaging;

import com.assignment.payment.application.usecase.ProcessPaymentUseCase;
import com.assignment.payment.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderEventListener(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void onOrderCreated(Map<String, Object> event) {
        log.info("[PAYMENT] Received order.created event: {}", event);
        processPaymentUseCase.execute(event);
    }
}
