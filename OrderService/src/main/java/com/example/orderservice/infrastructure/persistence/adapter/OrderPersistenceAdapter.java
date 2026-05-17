package com.example.orderservice.infrastructure.persistence.adapter;

import com.example.orderservice.domain.entities.Order;
import com.example.orderservice.domain.interfaces.OrderRepository;
import com.example.orderservice.infrastructure.persistence.repository.SpringDataOrderRepository;

public class OrderPersistenceAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    public OrderPersistenceAdapter(
            SpringDataOrderRepository springDataOrderRepository,
            OrderPersistenceMapper orderPersistenceMapper
    ) {
        this.springDataOrderRepository = springDataOrderRepository;
        this.orderPersistenceMapper = orderPersistenceMapper;
    }

    @Override
    public Order save(Order order) {
        return orderPersistenceMapper.toDomain(
                springDataOrderRepository.save(orderPersistenceMapper.toEntity(order))
        );
    }
}
