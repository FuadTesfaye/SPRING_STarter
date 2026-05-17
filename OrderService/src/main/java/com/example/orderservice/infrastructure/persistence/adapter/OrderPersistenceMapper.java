package com.example.orderservice.infrastructure.persistence.adapter;

import com.example.orderservice.domain.entities.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.infrastructure.persistence.entity.OrderJpaEntity;

public class OrderPersistenceMapper {

    public OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setOrderId(order.orderId());
        entity.setProductId(order.productId());
        entity.setQuantity(order.quantity());
        entity.setAmount(order.amount());
        entity.setStatus(order.status().name());
        entity.setInventoryStatus(order.inventoryStatus());
        entity.setPaymentStatus(order.paymentStatus());
        entity.setShipmentId(order.shipmentId());
        entity.setShipmentStatus(order.shipmentStatus());
        entity.setNotificationStatus(order.notificationStatus());
        entity.setMessage(order.message());
        return entity;
    }

    public Order toDomain(OrderJpaEntity entity) {
        return new Order(
                entity.getOrderId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getAmount(),
                OrderStatus.valueOf(entity.getStatus()),
                entity.getInventoryStatus(),
                entity.getPaymentStatus(),
                entity.getShipmentId(),
                entity.getShipmentStatus(),
                entity.getNotificationStatus(),
                entity.getMessage()
        );
    }
}
