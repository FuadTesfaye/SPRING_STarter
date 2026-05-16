package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentCompletedEvent {
    private String paymentId;
private String orderId;
private java.math.BigDecimal amount;
private java.time.LocalDateTime timestamp;
}
