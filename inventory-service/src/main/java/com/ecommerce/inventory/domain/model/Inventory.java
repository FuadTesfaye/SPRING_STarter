package com.ecommerce.inventory.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer availableStock;

    @Column(nullable = false)
    private Integer reservedStock;

    // Domain behavior
    public boolean reserve(int quantity) {
        if (availableStock >= quantity) {
            availableStock -= quantity;
            reservedStock += quantity;
            return true;
        }
        return false;
    }

    public void release(int quantity) {
        reservedStock -= quantity;
        availableStock += quantity;
    }

    public int getTotalStock() { return availableStock + reservedStock; }
}
