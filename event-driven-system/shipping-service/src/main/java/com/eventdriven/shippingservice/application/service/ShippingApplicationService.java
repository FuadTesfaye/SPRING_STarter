package com.eventdriven.shippingservice.application.service;

import com.eventdriven.shippingservice.domain.model.Shipment;
import com.eventdriven.shippingservice.domain.repository.ShipmentRepository;
import com.eventdriven.shippingservice.infrastructure.messaging.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ShippingApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(ShippingApplicationService.class);
    private final ShipmentRepository shipmentRepository;
    private final RabbitMQSender rabbitMQSender;
    private final Map<Long, Boolean> paymentReceived = new HashMap<>();
    private final Map<Long, Boolean> stockReserved = new HashMap<>();
    
    public ShippingApplicationService(ShipmentRepository shipmentRepository, RabbitMQSender rabbitMQSender) {
        this.shipmentRepository = shipmentRepository;
        this.rabbitMQSender = rabbitMQSender;
    }
    
    public void handlePaymentCompleted(Long orderId) {
        log.info("Payment completed for order: {}", orderId);
        paymentReceived.put(orderId, true);
        checkAndCreateShipment(orderId);
    }
    
    public void handleStockReserved(Long orderId) {
        log.info("Stock reserved for order: {}", orderId);
        stockReserved.put(orderId, true);
        checkAndCreateShipment(orderId);
    }
    
    private void checkAndCreateShipment(Long orderId) {
        if (Boolean.TRUE.equals(paymentReceived.get(orderId)) && 
            Boolean.TRUE.equals(stockReserved.get(orderId))) {
            
            String trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            Shipment shipment = new Shipment();
            shipment.setOrderId(orderId);
            shipment.setStatus("CREATED");
            shipment.setTrackingNumber(trackingNumber);
            shipment.setCreatedAt(LocalDateTime.now());
            shipmentRepository.save(shipment);
            
            rabbitMQSender.sendShipmentCreatedEvent(orderId, shipment.getId(), trackingNumber);
            log.info("Shipment created for order: {} with tracking: {}", orderId, trackingNumber);
            
            // Clean up
            paymentReceived.remove(orderId);
            stockReserved.remove(orderId);
        }
    }
}