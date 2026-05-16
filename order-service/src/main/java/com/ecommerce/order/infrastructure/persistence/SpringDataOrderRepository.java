package com.ecommerce.order.infrastructure.persistence;

import com.ecommerce.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
