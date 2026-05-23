package com.ecom.shipping.application.port;

import com.ecom.shipping.application.dto.ShipmentCreatedEvent;

public interface EventPublisher {
    void publishShipmentCreated(ShipmentCreatedEvent event);
}
