package com.company.inventory.infrastructure.messaging.rabbitmq;

import com.company.inventory.domain.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Inventory Service RECEIVED Order: " + event.getOrderId());
        System.out.println("Reserving " + event.getQuantity() + " units of " + event.getProductId());
        // Business logic would go here
    }
}
