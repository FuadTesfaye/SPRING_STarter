package com.ecommerce.inventory.domain.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StockFailedEvent {
    private String orderId;
    private String productId;
    private String reason;
    private LocalDateTime timestamp;
}
