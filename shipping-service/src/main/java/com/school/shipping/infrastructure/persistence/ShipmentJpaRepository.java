package com.school.shipping.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, Long> {
}
