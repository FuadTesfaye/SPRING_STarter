package com.example.shipping.application.port.out;

import com.example.shipping.domain.model.Shipment;

public interface ShippingEventPublisher {

    void publishShipmentCreated(Shipment shipment);
}
