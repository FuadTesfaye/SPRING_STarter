package com.example.inventoryservice.presentation.controllers;

import com.example.inventoryservice.application.dto.response.InventoryStatusResponse;
import com.example.inventoryservice.application.dto.response.InventoryUpdateResponse;
import com.example.inventoryservice.application.usecases.GetInventoryService;
import com.example.inventoryservice.application.usecases.ReserveInventoryService;
import com.example.inventoryservice.presentation.controllers.request.InventoryUpdateRequest;
import com.example.inventoryservice.presentation.mapper.InventoryPresentationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final GetInventoryService getInventoryUseCase;
    private final ReserveInventoryService reserveInventoryUseCase;
    private final InventoryPresentationMapper inventoryPresentationMapper;

    public InventoryController(
            GetInventoryService getInventoryUseCase,
            ReserveInventoryService reserveInventoryUseCase
    ) {
        this.getInventoryUseCase = getInventoryUseCase;
        this.reserveInventoryUseCase = reserveInventoryUseCase;
        this.inventoryPresentationMapper = new InventoryPresentationMapper();
    }

    @GetMapping
    public InventoryStatusResponse getInventory(@RequestParam Long productId) {
        return inventoryPresentationMapper.toPresentation(getInventoryUseCase.execute(productId));
    }

    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.OK)
    public InventoryUpdateResponse reserve(@RequestBody InventoryUpdateRequest request) {
        return inventoryPresentationMapper.toPresentation(
                reserveInventoryUseCase.execute(inventoryPresentationMapper.toApplication(request))
        );
    }
}
