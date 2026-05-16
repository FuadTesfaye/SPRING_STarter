package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentFailedEvent {
    private String paymentId;
private String orderId;
private String reason;
private java.time.LocalDateTime timestamp;
}
