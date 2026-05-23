package com.example.orderservice.infrastructure.persistence;
import com.example.orderservice.application.port.OrderRepositoryPort;
import com.example.orderservice.domain.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryAdapter implements OrderRepositoryPort {
    private final OrderJpaRepository jpa;
    public OrderRepositoryAdapter(OrderJpaRepository j) { this.jpa=j; }
    @Override public Order save(Order o) {
        OrderEntity e = new OrderEntity();
        e.setId(o.getId()); e.setUserId(o.getUserId()); e.setProductSku(o.getProductSku());
        e.setQuantity(o.getQuantity()); e.setAmount(o.getAmount()); e.setCreatedAt(o.getCreatedAt());
        jpa.save(e);
        return o;
    }
}
