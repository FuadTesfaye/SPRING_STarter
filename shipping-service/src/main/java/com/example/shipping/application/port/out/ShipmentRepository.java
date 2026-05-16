package com.example.shipping.application.port.out;

import com.example.shipping.domain.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    Optional<Shipment> findByOrderId(Long orderId);

    Optional<Shipment> findById(Long id);
}
