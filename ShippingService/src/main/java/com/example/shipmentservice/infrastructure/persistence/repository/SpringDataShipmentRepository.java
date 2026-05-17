package com.example.shipmentservice.infrastructure.persistence.repository;

import com.example.shipmentservice.infrastructure.persistence.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataShipmentRepository extends JpaRepository<ShipmentJpaEntity, String> {
}
