package com.app.shipping.application.ports;

import com.app.shipping.domain.Shipment;

public interface ShippingEventPublisher {
    void publishShipmentCreated(Shipment shipment);
}
