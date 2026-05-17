package com.example.shipping.presentation.controller;

import com.example.shipping.application.service.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@Tag(name = "Shipping", description = "Shipping management endpoints")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Operation(summary = "Create a shipment for a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Shipping created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
    @PostMapping("/create")
    public String createShipping(@RequestParam(name = "username") String username) {
        shippingService.createShipping(username);
        return "Shipping created for: " + username;
    }

    @Operation(summary = "Check shipping service status")
    @GetMapping
    public String status() {
        return "Shipping Service Running";
    }
}
