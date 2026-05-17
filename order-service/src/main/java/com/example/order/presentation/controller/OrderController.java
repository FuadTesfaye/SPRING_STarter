package com.example.order.presentation.controller;

import com.example.order.application.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam String username) {
        orderService.createOrder(username);
        return "Order created for: " + username;
    }

    @GetMapping
    public String orders() {
        return "Order Service Running";
    }
}
