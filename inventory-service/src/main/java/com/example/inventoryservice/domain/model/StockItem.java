package com.example.inventoryservice.domain.model;
public class StockItem {
    private String sku; private int available;
    public StockItem() {}
    public StockItem(String sku, int available) { this.sku=sku; this.available=available; }
    public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public int getAvailable(){return available;} public void setAvailable(int v){available=v;}
    public boolean canReserve(int qty) { return available >= qty; }
    public void reserve(int qty) {
        if (!canReserve(qty)) throw new IllegalStateException("insufficient stock");
        available -= qty;
    }
}
