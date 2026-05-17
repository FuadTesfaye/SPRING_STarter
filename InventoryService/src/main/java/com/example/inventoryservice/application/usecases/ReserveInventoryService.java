package com.example.inventoryservice.application.usecases;

import com.example.inventoryservice.application.dto.request.InventoryReserveRequest;
import com.example.inventoryservice.application.dto.response.InventoryUpdateResponse;

public interface ReserveInventoryService {

    InventoryUpdateResponse execute(InventoryReserveRequest request);
}
