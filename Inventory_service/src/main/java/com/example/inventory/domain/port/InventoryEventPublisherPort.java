package com.example.inventory.domain.port;


public interface InventoryEventPublisherPort {

    void publishReserved(String orderId, String productId, int quantity);

    void publishFailed(String orderId, String reason);
}
