package com.example.order.infrastructure.messaging;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.OrderCreatedEvent;
import com.example.order.application.port.out.OrderEventPublisher;
import com.example.order.domain.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventPublisher implements OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderCreatedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getCreatedAt()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.ORDER_CREATED, event);
    }
}
