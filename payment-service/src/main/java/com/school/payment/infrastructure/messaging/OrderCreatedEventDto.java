package com.school.payment.infrastructure.messaging;

import lombok.Data;
import java.math.BigDecimal;

// DTO to deserialize the incoming order.created event
@Data
public class OrderCreatedEventDto {
    private Long orderId;
    private String studentId;
    private String studentName;
    private String feeType;
    private BigDecimal amount;
}
