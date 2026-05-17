package com.microservices.shipping.infrastructure.persistence.repository;

import com.microservices.shipping.infrastructure.persistence.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShipmentRepository extends JpaRepository<ShipmentJpaEntity, String> {
}
