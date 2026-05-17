package com.example.shippingservice.domain.repository;

import com.example.shippingservice.domain.model.Shipment;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(UUID id);
}
