package com.microservices.order.infrastructure.persistence.mapper;

import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderId;
import com.microservices.order.domain.model.OrderItem;
import com.microservices.order.domain.model.OrderStatus;
import com.microservices.order.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.microservices.order.infrastructure.persistence.entity.OrderJpaEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PERSISTENCE MAPPER: OrderPersistenceMapper
 * 
 * WHAT: Converts between Domain Order and JPA Entity
 * WHY: Domain objects can't have JPA annotations
 * 
 * CONVERSION FLOW:
 * Domain → JPA: When saving to database
 * JPA → Domain: When loading from database
 */
public final class OrderPersistenceMapper {
    
    private OrderPersistenceMapper() {
        // Utility class
    }
    
    /**
     * Domain Order → JPA Entity (for saving)
     */
    public static OrderJpaEntity toJpaEntity(Order order) {
        if (order == null) return null;
        
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId().getValue());
        entity.setUserId(order.getUserId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus().name());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
        
        // Convert items
        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
            .map(item -> {
                OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
                itemEntity.setProductId(item.getProductId());
                itemEntity.setProductName(item.getProductName());
                itemEntity.setQuantity(item.getQuantity());
                itemEntity.setPrice(item.getPrice());
                itemEntity.setOrder(entity);  // Set bidirectional relationship
                return itemEntity;
            })
            .collect(Collectors.toList());
        
        entity.setItems(itemEntities);
        return entity;
    }
    
    /**
     * JPA Entity → Domain Order (for loading)
     */
    public static Order toDomain(OrderJpaEntity entity) {
        if (entity == null) return null;
        
        // Convert items
        List<OrderItem> items = entity.getItems().stream()
            .map(itemEntity -> new OrderItem(
                itemEntity.getProductId(),
                itemEntity.getProductName(),
                itemEntity.getQuantity(),
                itemEntity.getPrice()
            ))
            .collect(Collectors.toList());
        
        return Order.reconstruct(
            OrderId.fromString(entity.getId().toString()),
            entity.getUserId(),
            items,
            entity.getTotalAmount(),
            OrderStatus.valueOf(entity.getStatus()),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
