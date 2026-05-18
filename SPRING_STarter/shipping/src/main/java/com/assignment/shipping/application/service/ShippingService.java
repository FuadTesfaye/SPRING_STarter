package com.assignment.shipping.application.service;

import com.assignment.shipping.application.dto.ShippingRequest;
import com.assignment.shipping.application.dto.ShippingResponse;
import com.assignment.shipping.domain.model.Shipment;
import com.assignment.shipping.domain.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ShippingService {
    
    private final ShipmentRepository shipmentRepository;
    private final RestClient restClient;
    
    @Value("${services.notification}")
    private String notificationServiceUrl;
    
    public ShippingService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
        this.restClient = RestClient.create();
    }
    
    public ShippingResponse createShipment(ShippingRequest request) {
        ShippingResponse response = new ShippingResponse();
        
        Shipment shipment = new Shipment();
        shipment.setOrderId(request.getOrderId());
        shipment.setEmail(request.getEmail());
        shipment.setProductName(request.getProductName());
        shipment.setQuantity(request.getQuantity());
        shipment.setStatus("SHIPPED");
        shipment.setTrackingNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        shipment.setShippedAt(LocalDateTime.now());
        
        shipmentRepository.save(shipment);
        
        response.setSuccess(true);
        response.setMessage("Shipment created successfully");
        response.setTrackingNumber(shipment.getTrackingNumber());
        
        System.out.println("🚚 Shipment created for order: " + request.getOrderId());
        System.out.println("   Tracking number: " + shipment.getTrackingNumber());
        
        sendNotification(request.getOrderId(), "Shipment created - " + shipment.getTrackingNumber());
        
        return response;
    }
    
    private void sendNotification(Long orderId, String message) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("service", "shipping-service");
            notification.put("event", message);
            notification.put("orderId", orderId);
            notification.put("timestamp", System.currentTimeMillis());
            
            restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/log")
                .body(notification)
                .retrieve();
            
        } catch (Exception e) {
            System.err.println("Notification failed (service may not be running): " + e.getMessage());
        }
    }
}