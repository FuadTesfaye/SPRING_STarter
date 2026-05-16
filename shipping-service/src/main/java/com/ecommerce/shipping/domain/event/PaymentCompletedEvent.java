package com.ecommerce.shipping.domain.event;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentCompletedEvent {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
