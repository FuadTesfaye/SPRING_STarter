package com.example.payment.infrastructure.messaging;

import com.example.payment.domain.port.PaymentEventPublisherPort;
import com.example.payment.infrastructure.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher implements PaymentEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public void publishCompleted(String orderId, double amount) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "payment.completed",
                new PaymentCompletedEvent(orderId, amount)
        );
    }

    public void publishFailed(String orderId, String reason) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "payment.failed",
                new PaymentFailedEvent(orderId, reason)
        );
    }
}
