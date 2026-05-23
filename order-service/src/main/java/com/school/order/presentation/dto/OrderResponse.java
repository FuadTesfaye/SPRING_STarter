package com.school.order.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String studentId;
    private String studentName;
    private String feeType;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
}
