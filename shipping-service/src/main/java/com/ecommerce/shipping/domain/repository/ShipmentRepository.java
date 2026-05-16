package com.ecommerce.shipping.domain.repository;

import com.ecommerce.shipping.domain.model.Shipment;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findByOrderId(String orderId);
    List<Shipment> findAll();
}
