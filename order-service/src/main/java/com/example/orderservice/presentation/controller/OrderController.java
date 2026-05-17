package com.example.orderservice.presentation.controller;


import com.example.orderservice.application.service.OrderService;
import com.example.orderservice.presentation.dto.OrderRequestDTO;
import com.example.orderservice.presentation.dto.OrderResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }
}
