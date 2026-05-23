package com.assignment.shipping.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaShipmentRepository extends JpaRepository<ShipmentEntity, String> {
    Optional<ShipmentEntity> findByOrderId(String orderId);
}
