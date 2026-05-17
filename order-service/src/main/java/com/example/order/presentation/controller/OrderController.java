package com.example.order.presentation.controller;

import com.example.order.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create an order for a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
    @PostMapping("/create")
    public String createOrder(@RequestParam(name = "username") String username) {
        orderService.createOrder(username);
        return "Order created for: " + username;
    }

    @Operation(summary = "Check order service status")
    @GetMapping
    public String orders() {
        return "Order Service Running";
    }
}
