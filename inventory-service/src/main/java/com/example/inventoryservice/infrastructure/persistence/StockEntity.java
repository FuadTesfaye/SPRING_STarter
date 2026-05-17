package com.example.inventoryservice.infrastructure.persistence;
import jakarta.persistence.*;
@Entity @Table(name="stock")
public class StockEntity {
    @Id private String sku;
    private int available;
    public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public int getAvailable(){return available;} public void setAvailable(int v){available=v;}
}
