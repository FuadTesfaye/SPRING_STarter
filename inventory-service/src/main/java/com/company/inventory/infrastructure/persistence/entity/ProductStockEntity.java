package com.company.inventory.infrastructure.persistence.entity;

import com.company.inventory.domain.model.ProductStock;
import jakarta.persistence.*;

@Entity
@Table(name = "product_stocks")
public class ProductStockEntity {
    @Id
    private String productId;
    private Integer availableQuantity;

    public ProductStockEntity() {}

    public static ProductStockEntity fromDomain(ProductStock stock) {
        ProductStockEntity entity = new ProductStockEntity();
        entity.productId = stock.getProductId();
        entity.availableQuantity = stock.getAvailableQuantity();
        return entity;
    }

    public ProductStock toDomain() {
        return new ProductStock(productId, availableQuantity);
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }
}
