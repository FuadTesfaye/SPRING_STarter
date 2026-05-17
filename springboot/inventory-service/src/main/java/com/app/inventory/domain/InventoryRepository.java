package com.app.inventory.domain;

public interface InventoryRepository {
    boolean checkAndReserve(String sku, int quantity);
}
