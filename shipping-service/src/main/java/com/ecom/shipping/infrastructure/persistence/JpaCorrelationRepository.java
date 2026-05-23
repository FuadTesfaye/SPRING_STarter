package com.ecom.shipping.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaCorrelationRepository extends JpaRepository<ShippingCorrelationEntity, UUID> {}
