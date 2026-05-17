package com.example.shipmentservice.domain.services;

import com.example.shipmentservice.domain.entities.Shipment;
import com.example.shipmentservice.domain.enums.ShipmentStatus;
import java.util.UUID;

public class ShipmentDomainService {

    public Shipment createShipment(String orderId, Long productId, int quantity) {
        return new Shipment(UUID.randomUUID().toString(), orderId, productId, quantity, ShipmentStatus.SHIPMENT_CREATED);
    }
}
