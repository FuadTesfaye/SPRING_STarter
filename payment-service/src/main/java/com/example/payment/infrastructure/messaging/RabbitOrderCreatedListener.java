package com.example.payment.infrastructure.messaging;

import com.example.payment.application.usecases.ProcessPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitOrderCreatedListener {
    private final ProcessPaymentUseCase processPaymentUseCase;

    @RabbitListener(queues = RabbitPaymentConfig.QUEUE_ORDER_CREATED)
    public void handleOrderCreated(Map<String, Object> message) {
        Long orderId = Long.valueOf(message.get("orderId").toString());
        Double amount = Double.valueOf(message.get("totalAmount").toString());

        processPaymentUseCase.processOrderPayment(orderId, amount);
    }
}