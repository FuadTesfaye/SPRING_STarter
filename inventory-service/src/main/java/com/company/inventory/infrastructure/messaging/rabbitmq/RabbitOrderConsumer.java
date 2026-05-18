package com.company.inventory.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitOrderConsumer {

    @RabbitListener(queues = "inventory.order.queue")
    public void handleOrderCreated(Map<String, Object> message) {
        System.out.println("Inventory Service RECEIVED Order: " + message.get("orderId"));
        String productId = (String) message.get("productId");
        Integer quantity = (Integer) message.get("quantity");
        
        System.out.println("Reserving " + quantity + " units of Product: " + productId);
        // Business logic to check DB and reserve stock would go here
    }
}
