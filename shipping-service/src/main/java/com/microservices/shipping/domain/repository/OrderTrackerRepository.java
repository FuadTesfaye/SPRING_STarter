package com.microservices.shipping.domain.repository;

import com.microservices.shipping.domain.model.OrderTracker;
import java.util.Optional;

public interface OrderTrackerRepository {
    OrderTracker save(OrderTracker tracker);
    Optional<OrderTracker> findByOrderId(String orderId);
}
