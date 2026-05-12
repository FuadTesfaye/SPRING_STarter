package com.microservices.shippingservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingJpaRepository
        extends JpaRepository<ShippingEntity, Long> {
}