package com.company.shipping.infrastructure.persistence.repository;

import com.company.shipping.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShippingRepository extends JpaRepository<ShipmentEntity, Long> {
}
