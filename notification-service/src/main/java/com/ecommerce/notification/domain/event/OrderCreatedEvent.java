package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderCreatedEvent {
    private String orderId;
private String userId;
private String productId;
private Integer quantity;
private java.math.BigDecimal amount;
private String shippingAddress;
private java.time.LocalDateTime timestamp;
}
