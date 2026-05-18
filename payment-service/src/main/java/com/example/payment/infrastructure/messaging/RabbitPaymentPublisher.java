package com.example.payment.infrastructure.messaging;

import com.example.payment.application.ports.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitPaymentPublisher implements PaymentEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishPaymentCompleted(Long orderId) {
        rabbitTemplate.convertAndSend("app.exchange", "payment.completed", Map.of("orderId", orderId));
        System.out.println("Published payment.completed for Order: " + orderId);
    }

    @Override
    public void publishPaymentFailed(Long orderId, String reason) {
        rabbitTemplate.convertAndSend("app.exchange", "payment.failed", Map.of("orderId", orderId, "reason", reason));
        System.out.println("Published payment.failed for Order: " + orderId);
    }
}