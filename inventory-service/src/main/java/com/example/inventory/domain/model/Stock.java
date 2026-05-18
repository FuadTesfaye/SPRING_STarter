package com.example.inventory.domain.model;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Stock {
    private Long id;
    private String productId;
    private Integer quantity;
}