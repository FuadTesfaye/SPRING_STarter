package com.example.inventory.infrastructure.persistence;

import com.example.inventory.domain.model.Inventory;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
    private int reservedQuantity;

    public InventoryEntity() {}

    public static InventoryEntity fromDomain(Inventory inventory) {
        InventoryEntity entity = new InventoryEntity();
        entity.id = inventory.getId();
        entity.productId = inventory.getProductId();
        entity.productName = inventory.getProductName();
        entity.quantity = inventory.getQuantity();
        entity.reservedQuantity = inventory.getReservedQuantity();
        return entity;
    }

    public Inventory toDomain() {
        Inventory inventory = new Inventory(productId, productName, quantity);
        return inventory;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}