package com.microservices.order.application.mapper;

import com.microservices.order.application.dto.request.CreateOrderRequest;
import com.microservices.order.application.dto.response.OrderResponse;
import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MAPPER: OrderMapper
 * 
 * WHAT: Converts between DTOs and Domain objects
 * WHY: 
 * - Domain objects shouldn't leak to API layer
 * - DTOs are for data transfer, not business logic
 * - Centralized conversion logic
 * 
 * CONVERSION FLOW:
 * Request DTO → Domain Objects (for processing)
 * Domain Objects → Response DTO (for returning to client)
 */
public final class OrderMapper {
    
    private OrderMapper() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Convert CreateOrderRequest items → List of OrderItem domain objects
     * 
     * WHY: Domain works with OrderItem objects, not DTOs
     */
    public static List<OrderItem> toDomainItems(List<CreateOrderRequest.OrderItemRequest> itemRequests) {
        if (itemRequests == null) {
            return List.of();
        }
        
        return itemRequests.stream()
            .map(item -> new OrderItem(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getPrice()
            ))
            .collect(Collectors.toList());
    }
    
    /**
     * Convert Order domain → OrderResponse DTO
     * 
     * WHY: Client needs clean DTO, not domain object
     */
    public static OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId().getValue().toString());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        
        // Map items
        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
            .map(item -> {
                OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
                itemResponse.setProductId(item.getProductId());
                itemResponse.setProductName(item.getProductName());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPrice(item.getPrice());
                itemResponse.setTotalPrice(item.getTotalPrice());
                return itemResponse;
            })
            .collect(Collectors.toList());
        
        response.setItems(itemResponses);
        return response;
    }
    
    /**
     * Convert List of Orders → List of OrderResponse DTOs
     */
    public static List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null) {
            return List.of();
        }
        
        return orders.stream()
            .map(OrderMapper::toResponse)
            .collect(Collectors.toList());
    }
}
