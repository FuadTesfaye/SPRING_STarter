package com.school.shipping.infrastructure.messaging;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentCompletedEventDto {
    private Long orderId;
    private Long paymentId;
    private String studentId;
    private BigDecimal amount;
    private String reference;
}
