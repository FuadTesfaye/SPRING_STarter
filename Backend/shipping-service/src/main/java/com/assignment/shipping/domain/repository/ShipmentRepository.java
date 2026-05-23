package com.assignment.shipping.domain.repository;

import com.assignment.shipping.domain.model.Shipment;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findByOrderId(UUID orderId);
}
