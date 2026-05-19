package com.example.shippingservice.messaging;

import com.example.shippingservice.config.RabbitMQConfig;
import com.example.shippingservice.dto.InventoryEvent;
import com.example.shippingservice.dto.PaymentEvent;
import com.example.shippingservice.dto.ShippingEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShippingListener {

    private final RabbitTemplate rabbitTemplate;

    public ShippingListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Trigger shipping ONLY if BOTH conditions are satisfied in real systems
    // For simplicity: we assume both events arrive

    @RabbitListener(queues = "shipping.queue")
    public void handleShipping(Object event) {

        System.out.println("Shipping received event: " + event);

        // MOCK LOGIC (simple version for assignment)
        ShippingEvent shippingEvent = new ShippingEvent(1L, "CREATED");

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "shipment.created",
                shippingEvent
        );

        System.out.println("SHIPMENT CREATED");
    }
}