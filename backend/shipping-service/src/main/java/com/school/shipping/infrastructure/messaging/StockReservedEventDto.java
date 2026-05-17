package com.school.shipping.infrastructure.messaging;

import lombok.Data;

@Data
public class StockReservedEventDto {
    private Long orderId;
    private String studentId;
    private String feeType;
}
