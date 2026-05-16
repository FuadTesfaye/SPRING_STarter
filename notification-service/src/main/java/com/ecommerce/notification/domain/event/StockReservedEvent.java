package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StockReservedEvent {
    private String orderId;
private String productId;
private Integer quantity;
private java.time.LocalDateTime timestamp;
}
