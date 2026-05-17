package com.example.inventoryservice.application.usecases;

import com.example.inventoryservice.application.dto.response.InventoryStatusResponse;

public interface GetInventoryService {

    InventoryStatusResponse execute(Long productId);
}
