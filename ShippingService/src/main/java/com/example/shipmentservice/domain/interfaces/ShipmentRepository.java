package com.example.shipmentservice.domain.interfaces;

import com.example.shipmentservice.domain.entities.Shipment;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);
}
