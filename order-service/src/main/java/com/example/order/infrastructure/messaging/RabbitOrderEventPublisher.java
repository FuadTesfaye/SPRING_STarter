package com.example.order.infrastructure.messaging;

import com.example.order.application.ports.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitOrderEventPublisher implements OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishOrderCreated(Long orderId, Long userId, Double totalAmount) {
        Map<String, Object> message = Map.of(
                "orderId", orderId,
                "userId", userId,
                "totalAmount", totalAmount,
                "event", "ORDER_CREATED"
        );
        rabbitTemplate.convertAndSend("app.exchange", "order.created", message);
        System.out.println("Published OrderCreated event for Order ID: " + orderId);
    }
}