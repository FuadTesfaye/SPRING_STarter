package com.assignment.order.application.service;

import com.assignment.order.application.dto.OrderRequest;
import com.assignment.order.domain.model.Order;
import com.assignment.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final RestClient restClient;
    
    @Value("${services.notification}")
    private String notificationServiceUrl;
    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.restClient = RestClient.create();
    }
    
    public Order createOrder(OrderRequest request) {
        // Create order
        Order order = new Order();
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setEmail(request.getEmail());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        
        Order savedOrder = orderRepository.save(order);
        System.out.println("📦 Order created: " + savedOrder.getId());
        
        // Send notification
        sendNotification(savedOrder.getId(), "Order created - PENDING");
        
        savedOrder.setStatus("CONFIRMED");
        orderRepository.save(savedOrder);
        
        sendNotification(savedOrder.getId(), "Order confirmed");
        
        return savedOrder;
    }
    
    private void sendNotification(Long orderId, String message) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("service", "order-service");
            notification.put("event", message);
            notification.put("orderId", orderId);
            notification.put("timestamp", System.currentTimeMillis());
            
            restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/log")
                .body(notification)
                .retrieve();
            
            System.out.println("📢 Notification sent: " + message);
            
        } catch (Exception e) {
            System.err.println("⚠️ Notification failed (service may not be running): " + e.getMessage());
        }
    }
}