package com.microservices.order.application.service;

import com.microservices.order.application.dto.request.CreateOrderRequest;
import com.microservices.order.application.dto.response.OrderResponse;
import com.microservices.order.application.mapper.OrderMapper;
import com.microservices.order.domain.exception.OrderDomainException;
import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderItem;
import com.microservices.order.domain.service.OrderDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * APPLICATION SERVICE: OrderApplicationService
 * 
 * WHAT: Orchestrates order use cases
 * WHY:
 * - Translates DTOs to domain objects
 * - Coordinates domain services
 * - Handles application-level concerns (logging, transactions)
 * - Provides clean API for presentation layer
 * 
 * USE CASES:
 * 1. Create Order
 * 2. Get Order by ID
 * 3. Get Orders by User
 * 4. Get All Orders
 */
public class OrderApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(OrderApplicationService.class);
    
    private final OrderDomainService orderDomainService;
    
    // Constructor injection
    public OrderApplicationService(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }
    
    /**
     * USE CASE: Create a new order
     * 
     * FLOW:
     * 1. Extract data from DTO
     * 2. Convert to domain objects
     * 3. Call domain service (validates + publishes event)
     * 4. Map result to response DTO
     */
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for user: {}", request.getUserId());
        
        try {
            // Convert DTO items to domain OrderItem objects
            List<OrderItem> items = OrderMapper.toDomainItems(request.getItems());
            
            // Delegate to domain service
            Order order = orderDomainService.createOrder(request.getUserId(), items);
            
            log.info("Order created successfully: {}", order.getId());
            
            // Return response
            return OrderMapper.toResponse(order);
            
        } catch (OrderDomainException e) {
            log.error("Failed to create order: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * USE CASE: Get order by ID
     */
    public OrderResponse getOrder(String orderId) {
        log.info("Fetching order: {}", orderId);
        
        try {
            Order order = orderDomainService.findById(orderId);
            return OrderMapper.toResponse(order);
        } catch (OrderDomainException e) {
            log.error("Failed to fetch order: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * USE CASE: Get orders by user ID
     */
    public List<OrderResponse> getOrdersByUser(String userId) {
        log.info("Fetching orders for user: {}", userId);
        
        List<Order> orders = orderDomainService.findByUserId(userId);
        return OrderMapper.toResponseList(orders);
    }
    
    /**
     * USE CASE: Get all orders
     */
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        
        List<Order> orders = orderDomainService.findAll();
        return OrderMapper.toResponseList(orders);
    }
}
