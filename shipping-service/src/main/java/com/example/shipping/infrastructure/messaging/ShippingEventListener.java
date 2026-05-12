package com.microservices.shippingservice.infrastructure.messaging;

import com.microservices.shippingservice.application.event.InventoryEvent;
import com.microservices.shippingservice.application.service.ShippingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventListener {

    private final ShippingService shippingService;

    public ShippingEventListener(
            ShippingService shippingService) {

        this.shippingService = shippingService;
    }

    @RabbitListener(
            queues = "inventory.updated.queue")
    public void handle(
            InventoryEvent event) {

        shippingService.createShipping(
                event.getUsername());
    }
}