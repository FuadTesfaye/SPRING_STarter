package com.example.order.domain.model;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private String productId;
    private Integer quantity;
    private Double price;
    private String status; // e.g., "CREATED"
}