package com.example.shippingservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
}
