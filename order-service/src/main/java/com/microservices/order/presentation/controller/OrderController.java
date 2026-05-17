package com.microservices.order.presentation.controller;

import com.microservices.order.application.dto.request.CreateOrderRequest;
import com.microservices.order.application.dto.response.OrderResponse;
import com.microservices.order.application.service.OrderApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ORDER CONTROLLER
 * 
 * WHAT: REST API endpoints for order management
 * 
 * ENDPOINTS:
 * - POST /api/orders - Create new order
 * - GET /api/orders/{id} - Get order by ID
 * - GET /api/orders/user/{userId} - Get orders by user
 * - GET /api/orders - Get all orders
 * - GET /api/orders/health - Health check
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order Management API")
public class OrderController {
    
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    private final OrderApplicationService orderApplicationService;
    
    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }
    
    /**
     * CREATE ORDER
     * 
     * POST /api/orders
     * 
     * Request Body:
     * {
     *   "userId": "user-uuid",
     *   "items": [
     *     {
     *       "productId": "prod-001",
     *       "productName": "Laptop",
     *       "quantity": 1,
     *       "price": 999.99
     *     },
     *     {
     *       "productId": "prod-002",
     *       "productName": "Mouse",
     *       "quantity": 2,
     *       "price": 29.99
     *     }
     *   ]
     * }
     * 
     * Success Response (201):
     * {
     *   "orderId": "uuid",
     *   "userId": "user-uuid",
     *   "items": [...],
     *   "totalAmount": 1059.97,
     *   "status": "PENDING",
     *   "createdAt": "2026-05-12T..."
     * }
     */
    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        
        log.info("REST request: POST /api/orders - user: {}", request.getUserId());
        
        OrderResponse response = orderApplicationService.createOrder(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * GET ORDER BY ID
     * 
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String id) {
        
        log.info("REST request: GET /api/orders/{}", id);
        
        OrderResponse response = orderApplicationService.getOrder(id);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET ORDERS BY USER
     * 
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders found")
    })
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable String userId) {
        
        log.info("REST request: GET /api/orders/user/{}", userId);
        
        List<OrderResponse> responses = orderApplicationService.getOrdersByUser(userId);
        
        return ResponseEntity.ok(responses);
    }
    
    /**
     * GET ALL ORDERS
     * 
     * GET /api/orders
     */
    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved")
    })
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        
        log.info("REST request: GET /api/orders");
        
        List<OrderResponse> responses = orderApplicationService.getAllOrders();
        
        return ResponseEntity.ok(responses);
    }
    
    /**
     * HEALTH CHECK
     * 
     * GET /api/orders/health
     */
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service is running!");
    }
}
