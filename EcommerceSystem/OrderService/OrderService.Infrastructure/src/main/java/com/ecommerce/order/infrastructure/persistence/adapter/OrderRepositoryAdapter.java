package com.ecommerce.order.infrastructure.persistence.adapter;

import com.ecommerce.order.domain.entity.Order;
import com.ecommerce.order.domain.entity.OrderItem;
import com.ecommerce.order.domain.repository.OrderRepository;
import com.ecommerce.order.infrastructure.persistence.entity.OrderEntity;
import com.ecommerce.order.infrastructure.persistence.entity.OrderItemEntity;
import com.ecommerce.order.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .items(order.getItems() != null ? order.getItems().stream().map(item ->
                        OrderItemEntity.builder()
                                .id(item.getId())
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build()
                ).collect(Collectors.toList()) : null)
                .build();
    }

    private Order toDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .totalAmount(entity.getTotalAmount())
                .status(Order.OrderStatus.valueOf(entity.getStatus()))
                .items(entity.getItems() != null ? entity.getItems().stream().map(item ->
                        OrderItem.builder()
                                .id(item.getId())
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build()
                ).collect(Collectors.toList()) : null)
                .build();
    }
}
