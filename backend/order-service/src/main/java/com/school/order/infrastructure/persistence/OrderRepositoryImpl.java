package com.school.order.infrastructure.persistence;

import com.school.order.application.port.OrderRepository;
import com.school.order.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private OrderJpaEntity toEntity(Order o) {
        OrderJpaEntity e = new OrderJpaEntity();
        e.setId(o.getId());
        e.setStudentId(o.getStudentId());
        e.setStudentName(o.getStudentName());
        e.setFeeType(o.getFeeType());
        e.setAmount(o.getAmount());
        e.setStatus(o.getStatus());
        e.setCreatedAt(o.getCreatedAt());
        return e;
    }

    private Order toDomain(OrderJpaEntity e) {
        Order o = new Order();
        o.setId(e.getId());
        o.setStudentId(e.getStudentId());
        o.setStudentName(e.getStudentName());
        o.setFeeType(e.getFeeType());
        o.setAmount(e.getAmount());
        o.setStatus(e.getStatus());
        o.setCreatedAt(e.getCreatedAt());
        return o;
    }
}
