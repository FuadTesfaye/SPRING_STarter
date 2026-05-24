package com.ecom.shipping.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
    java.util.Optional<ShipmentEntity> findByOrderId(UUID orderId);
}
