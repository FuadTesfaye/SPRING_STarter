package com.example.shipping.application.service;

import com.example.shipping.domain.event.ShipmentCreatedEvent;
import com.example.shipping.domain.model.Shipment;
import com.example.shipping.domain.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ShippingService {
    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);
    private final ShipmentRepository shipmentRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Map<UUID, Boolean> paymentStatus = new ConcurrentHashMap<>();
    private final Map<UUID, Boolean> stockStatus = new ConcurrentHashMap<>();

    public ShippingService(ShipmentRepository shipmentRepository, RabbitTemplate rabbitTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handlePaymentCompleted(UUID orderId, UUID userId) {
        paymentStatus.put(orderId, true);
        checkAndCreateShipment(orderId, userId);
    }

    public void handleStockReserved(UUID orderId, UUID userId) {
        stockStatus.put(orderId, true);
        checkAndCreateShipment(orderId, userId);
    }

    private void checkAndCreateShipment(UUID orderId, UUID userId) {
        if (paymentStatus.getOrDefault(orderId, false) && 
            stockStatus.getOrDefault(orderId, false)) {
            
            Shipment shipment = new Shipment(orderId, userId);
            shipment = shipmentRepository.save(shipment);
            
            ShipmentCreatedEvent event = new ShipmentCreatedEvent(
                orderId, userId, shipment.getId(), shipment.getTrackingNumber()
            );
            rabbitTemplate.convertAndSend("app.exchange", "shipment.created", event);
            
            log.info("Shipment created for order: {}", orderId);
            
            paymentStatus.remove(orderId);
            stockStatus.remove(orderId);
        }
    }
}