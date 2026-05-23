package com.school.order.presentation.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    private String studentId;
    private String studentName;
    private String feeType;   // TUITION, EXAM, LIBRARY, SPORTS
    private BigDecimal amount;
}
