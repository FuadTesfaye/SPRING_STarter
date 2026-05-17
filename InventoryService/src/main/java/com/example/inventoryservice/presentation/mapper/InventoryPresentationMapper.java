package com.example.inventoryservice.presentation.mapper;

import com.example.inventoryservice.application.dto.request.InventoryReserveRequest;
import com.example.inventoryservice.application.dto.response.InventoryStatusResponse;
import com.example.inventoryservice.application.dto.response.InventoryUpdateResponse;
import com.example.inventoryservice.presentation.controllers.request.InventoryUpdateRequest;

public class InventoryPresentationMapper {

    public InventoryReserveRequest toApplication(InventoryUpdateRequest request) {
        return new InventoryReserveRequest(request.productId(), request.quantity());
    }

    public InventoryStatusResponse toPresentation(InventoryStatusResponse response) {
        return response;
    }

    public InventoryUpdateResponse toPresentation(InventoryUpdateResponse response) {
        return response;
    }
}
