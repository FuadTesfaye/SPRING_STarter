package com.app.shipping.infrastructure.messaging;

import com.app.shipping.application.ports.ShippingEventPublisher;
import com.app.shipping.domain.Shipment;
import com.app.shipping.domain.ShipmentCreated;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQShippingEventPublisher implements ShippingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQShippingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishShipmentCreated(Shipment shipment) {
        ShipmentCreated event = new ShipmentCreated(shipment.getOrderId(), shipment.getTrackingNumber());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.SHIPMENT_CREATED_ROUTING_KEY, event);
    }
}
