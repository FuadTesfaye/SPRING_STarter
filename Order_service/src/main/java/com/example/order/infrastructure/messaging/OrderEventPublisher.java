package com.example.order.infrastructure.messaging;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.OrderEventPublisherPort;
import com.example.order.infrastructure.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher implements OrderEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(Order order, Long userId) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getOrderId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                userId
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "order.created", event);
    }
}
