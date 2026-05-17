package com.example.shippingservice.infrastructure.messaging;

import com.example.shippingservice.domain.model.Shipment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ShippingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishShipmentCreated(Shipment shipment) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.SHIPMENT_CREATED_KEY,
                shipment
        );
        System.out.println("📤 shipment.created event published for order: " + shipment.getOrderId());
    }
}