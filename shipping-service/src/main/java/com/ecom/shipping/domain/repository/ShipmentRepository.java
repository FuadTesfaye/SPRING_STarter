package com.ecom.shipping.domain.repository;

import com.ecom.shipping.domain.model.Shipment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    List<Shipment> findAll();
    Optional<Shipment> findByOrderId(UUID orderId);
}
