package com.microservices.order.infrastructure.persistence.repository;

import com.microservices.order.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * SPRING DATA JPA REPOSITORY
 * 
 * WHAT: Spring Data interface for Order database operations
 * WHY: Automatic CRUD and query generation
 * 
 * Spring automatically implements:
 * - save(), findById(), findAll(), deleteById()
 * - findByUserId() → SELECT * FROM orders WHERE user_id = ?
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
    
    /**
     * Find all orders for a specific user
     */
    List<OrderJpaEntity> findByUserId(String userId);
}
