package com.ecommerce.payment.domain.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentFailedEvent {
    private String paymentId;
    private String orderId;
    private String reason;
    private LocalDateTime timestamp;
}
