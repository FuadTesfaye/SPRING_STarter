package com.company.order.presentation.rest;

import com.company.order.application.usecase.CreateOrderUseCase;
import com.company.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order order = createOrderUseCase.execute(
                request.getCustomerId(),
                request.getProductId(),
                request.getQuantity(),
                request.getPricePerUnit()
        );
        return ResponseEntity.ok(order);
    }
}
