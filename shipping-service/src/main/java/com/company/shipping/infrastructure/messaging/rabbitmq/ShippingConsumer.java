package com.company.shipping.infrastructure.messaging.rabbitmq;

import com.company.shipping.domain.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingConsumer {

    @RabbitListener(queues = "shipping.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Shipping Service RECEIVED Order: " + event.getOrderId());
        System.out.println("Scheduling shipment for " + event.getProductId());
        // Business logic would go here
    }
}
