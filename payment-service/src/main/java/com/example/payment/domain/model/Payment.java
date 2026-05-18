package com.example.payment.domain.model;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Payment {
    private Long id;
    private Long orderId;
    private Double amount;
    private String status; // "SUCCESS", "FAILED"
}