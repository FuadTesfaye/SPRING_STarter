package com.company.inventory.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shade_inventory")
public class ShadeInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productId;
    private String shadeCode;
    
    private int quantity;
    private int reservedQuantity;
    private int sampleQuantity;
    
    private LocalDate expiryDate;
    private int restockThreshold;
    private boolean isSampleUnit;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getShadeCode() { return shadeCode; }
    public void setShadeCode(String shadeCode) { this.shadeCode = shadeCode; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
    public int getSampleQuantity() { return sampleQuantity; }
    public void setSampleQuantity(int sampleQuantity) { this.sampleQuantity = sampleQuantity; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public int getRestockThreshold() { return restockThreshold; }
    public void setRestockThreshold(int restockThreshold) { this.restockThreshold = restockThreshold; }
    public boolean isSampleUnit() { return isSampleUnit; }
    public void setSampleUnit(boolean sampleUnit) { isSampleUnit = sampleUnit; }
}
