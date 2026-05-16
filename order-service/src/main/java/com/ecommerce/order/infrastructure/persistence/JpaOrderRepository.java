package com.ecommerce.order.infrastructure.persistence;

import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository {
    private final SpringDataOrderRepository repo;

    @Override public Order save(Order o) { return repo.save(o); }
    @Override public Optional<Order> findById(String id) { return repo.findById(id); }
    @Override public List<Order> findByUserId(String uid) { return repo.findByUserId(uid); }
    @Override public List<Order> findAll() { return repo.findAll(); }
}
