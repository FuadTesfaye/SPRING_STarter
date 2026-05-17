package com.microservices.shipping.domain.event;

public interface ShippingEventPublisher {
    void publishShipmentCreated(ShipmentCreatedEvent event);
}
