package com.example.shipping.domain.repository;

import com.example.shipping.domain.model.Shipment;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
}