package com.assignment.inventory.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String productName;
    private Integer availableQuantity;
    
    public InventoryItem() {}
    
    public InventoryItem(String productName, Integer availableQuantity) {
        this.productName = productName;
        this.availableQuantity = availableQuantity;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }
}