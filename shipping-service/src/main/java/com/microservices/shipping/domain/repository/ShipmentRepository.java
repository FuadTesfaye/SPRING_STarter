package com.microservices.shipping.domain.repository;

import com.microservices.shipping.domain.model.Shipment;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(String id);
    List<Shipment> findAll();
}
