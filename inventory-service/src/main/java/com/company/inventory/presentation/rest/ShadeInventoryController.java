package com.company.inventory.presentation.rest;

import com.company.inventory.application.service.ShadeInventoryService;
import com.company.inventory.domain.model.ShadeInventory;
import com.company.shared.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ShadeInventoryController {

    private final ShadeInventoryService service;

    public ShadeInventoryController(ShadeInventoryService service) {
        this.service = service;
    }

    @GetMapping("/shade/{shadeCode}")
    public ResponseEntity<ApiResponse<ShadeInventory>> getShadeStock(@PathVariable String shadeCode) {
        return ResponseEntity.ok(ApiResponse.success(service.getShadeStock(shadeCode)));
    }

    @PostMapping("/shade/{shadeCode}/subscribe")
    public ResponseEntity<ApiResponse<Void>> subscribeToRestock(
            @PathVariable String shadeCode,
            @RequestHeader("X-User-Id") String userId) {
        service.subscribeToRestock(userId, shadeCode);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/expiring")
    public ResponseEntity<ApiResponse<List<ShadeInventory>>> getExpiringSoon(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.success(service.getExpiringSoon(days)));
    }
}
