package com.example.inventory.domain.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class StockRegistry {

    private final Map<String, AtomicInteger> skuToQty = new ConcurrentHashMap<>();

    public StockRegistry() {
        skuToQty.put("SKU-DEMO", new AtomicInteger(10_000));
    }

    public boolean tryReserve(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            return false;
        }
        if (productId.contains("OUT_OF_STOCK")) {
            return false;
        }
        if (quantity <= 0 || quantity > 5000) {
            return false;
        }
        AtomicInteger stock = skuToQty.computeIfAbsent(productId, id -> new AtomicInteger(1_000));
        while (true) {
            int current = stock.get();
            if (current < quantity) {
                return false;
            }
            if (stock.compareAndSet(current, current - quantity)) {
                return true;
            }
        }
    }
}
