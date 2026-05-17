package com.microservices.shipping.infrastructure.persistence.repository;

import com.microservices.shipping.infrastructure.persistence.entity.OrderTrackerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaOrderTrackerRepository extends JpaRepository<OrderTrackerJpaEntity, String> {
    Optional<OrderTrackerJpaEntity> findByOrderId(String orderId);
}
