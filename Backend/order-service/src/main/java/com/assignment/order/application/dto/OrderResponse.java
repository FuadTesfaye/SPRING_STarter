package com.assignment.order.application.dto;

import com.assignment.order.domain.model.Order;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
    UUID id,
    UUID userId,
    String eventId,
    String productName,
    int quantity,
    BigDecimal totalAmount,
    BigDecimal price,
    String status,
    Instant createdAt
) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(
            o.getId(), o.getUserId(), o.getEventId(), o.getProductName(),
            o.getQuantity(), o.getTotalAmount(), o.getPrice(), o.getStatus(), o.getCreatedAt()
        );
    }
}
