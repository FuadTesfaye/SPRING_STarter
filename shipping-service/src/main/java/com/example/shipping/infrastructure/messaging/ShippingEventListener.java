package com.example.shipping.infrastructure.messaging;

import com.example.shipping.application.service.ShippingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventListener {

    private final ShippingService shippingService;

    public ShippingEventListener(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_UPDATED_QUEUE)
    public void handleInventoryUpdated(String username) {
        System.out.println("Shipping-service received inventory.updated for: " + username);
        shippingService.createShipping(username);
    }
}
