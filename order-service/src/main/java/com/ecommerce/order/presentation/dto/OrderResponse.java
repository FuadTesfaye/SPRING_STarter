package com.ecommerce.order.presentation.dto;

import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.domain.model.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class OrderResponse {
    private String id;
    private String userId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
    private String shippingAddress;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order o) {
        return OrderResponse.builder()
                .id(o.getId()).userId(o.getUserId()).productId(o.getProductId())
                .quantity(o.getQuantity()).amount(o.getAmount())
                .shippingAddress(o.getShippingAddress()).status(o.getStatus())
                .createdAt(o.getCreatedAt()).build();
    }
}
