package com.example.inventory.application.port.out;

import com.example.inventory.domain.model.Reservation;

public interface InventoryEventPublisher {

    void publishStockReserved(Reservation reservation);

    void publishStockFailed(Long orderId, String failureReason);
}
