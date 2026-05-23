package com.school.inventory.infrastructure.messaging;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderCreatedEventDto {
    private Long orderId;
    private String studentId;
    private String studentName;
    private String feeType;
    private BigDecimal amount;
}
