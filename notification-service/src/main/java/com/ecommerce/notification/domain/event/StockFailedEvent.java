package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StockFailedEvent {
    private String orderId;
private String productId;
private String reason;
private java.time.LocalDateTime timestamp;
}
