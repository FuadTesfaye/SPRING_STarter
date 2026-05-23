package com.assignment.order.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByUserId(String userId);
}
