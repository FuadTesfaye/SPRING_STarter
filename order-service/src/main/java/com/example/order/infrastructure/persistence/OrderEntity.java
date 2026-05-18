package com.example.order.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String productId;
    private Integer quantity;
    private Double price;
    private String status;
}