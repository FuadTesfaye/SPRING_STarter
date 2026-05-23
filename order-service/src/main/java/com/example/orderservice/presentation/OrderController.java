package com.example.orderservice.presentation;
import com.example.orderservice.application.service.CreateOrderUseCase;
import com.example.orderservice.domain.model.Order;
import com.example.orderservice.presentation.dto.CreateOrderRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CreateOrderUseCase create;
    public OrderController(CreateOrderUseCase c) { this.create = c; }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequest req, HttpServletRequest http) {
        String uid = (String) http.getAttribute("userId");
        if (uid == null) return ResponseEntity.status(401).body("Unauthorized");
        Order o = create.create(UUID.fromString(uid), req.productSku, req.quantity, req.amount);
        return ResponseEntity.ok(o);
    }
}
