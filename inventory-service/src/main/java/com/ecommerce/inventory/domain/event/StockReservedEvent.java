package com.ecommerce.inventory.domain.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StockReservedEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    private LocalDateTime timestamp;
}
