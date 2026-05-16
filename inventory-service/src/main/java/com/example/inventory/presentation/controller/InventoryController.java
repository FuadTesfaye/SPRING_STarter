package com.example.inventory.presentation.controller;

import com.example.inventory.application.dto.ReservationResponse;
import com.example.inventory.application.service.InventoryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Inventory management endpoints")
public class InventoryController {

    private final InventoryApplicationService inventoryApplicationService;

    public InventoryController(InventoryApplicationService inventoryApplicationService) {
        this.inventoryApplicationService = inventoryApplicationService;
    }

    @GetMapping("/reservations/{reservationId}")
    @Operation(summary = "Get reservation details", description = "Retrieve details of a specific stock reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ReservationResponse getReservation(
            @Parameter(description = "Reservation ID", required = true)
            @PathVariable Long reservationId) {
        return inventoryApplicationService.getReservation(reservationId);
    }
}
