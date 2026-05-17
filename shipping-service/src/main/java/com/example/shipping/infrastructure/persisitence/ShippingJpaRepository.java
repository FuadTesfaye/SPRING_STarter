package com.example.shipping.infrastructure.persisitence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingJpaRepository extends JpaRepository<ShippingEntity, Long> {
}
