package com.example.shipping.infrastructure.messaging;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.ShipmentCreatedEvent;
import com.example.shipping.application.port.out.ShippingEventPublisher;
import com.example.shipping.domain.model.Shipment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventPublisherAdapter implements ShippingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ShippingEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishShipmentCreated(Shipment shipment) {
        ShipmentCreatedEvent event = new ShipmentCreatedEvent(
                shipment.getOrderId(),
                shipment.getId(),
                shipment.getStatus().toString(),
                shipment.getCreatedAt()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.SHIPMENT_CREATED, event);
    }
}
