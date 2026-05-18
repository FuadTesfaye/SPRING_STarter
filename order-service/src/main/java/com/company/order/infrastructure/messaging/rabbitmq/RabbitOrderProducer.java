package com.company.order.infrastructure.messaging.rabbitmq;

import com.company.order.domain.event.OrderCreatedEvent;
import com.company.order.domain.model.Order;
import com.company.order.domain.repository.IOrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitOrderProducer implements IOrderProducer {

    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.created";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendOrderCreatedEvent(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getPricePerUnit()
        );
        
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
        System.out.println("Order Event Published: " + event.getOrderId());
    }
}
