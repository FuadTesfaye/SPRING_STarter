package com.example.orderservice.application.interfaces;

import com.example.orderservice.application.dto.request.InventoryReserveRequest;
import com.example.orderservice.application.dto.response.InventoryReserveResponse;

public interface InventoryGateway {

    InventoryReserveResponse reserveInventory(InventoryReserveRequest request);
}
