package com.assignment.inventory.infrastructure.persistence;

import com.assignment.inventory.domain.model.Inventory;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    public String id;

    @Column(unique = true, nullable = false)
    public String productName;

    @Column(nullable = false)
    public int availableStock;

    public Instant updatedAt;

    public static InventoryEntity fromDomain(Inventory inv) {
        InventoryEntity e = new InventoryEntity();
        e.id = inv.getId().toString();
        e.productName = inv.getProductName();
        e.availableStock = inv.getAvailableStock();
        e.updatedAt = inv.getUpdatedAt();
        return e;
    }

    public Inventory toDomain() {
        return new Inventory(UUID.fromString(id), productName, availableStock, updatedAt);
    }
}
