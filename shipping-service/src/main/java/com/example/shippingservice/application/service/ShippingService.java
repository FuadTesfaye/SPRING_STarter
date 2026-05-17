package com.example.shippingservice.application.service;



import com.example.shippingservice.application.dto.PaymentCompletedEvent;
import com.example.shippingservice.application.dto.StockReservedEvent;
import com.example.shippingservice.domain.model.Shipment;
import com.example.shippingservice.domain.repository.ShipmentRepository;
import com.example.shippingservice.infrastructure.messaging.ShippingEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShippingService {

    private final ShipmentRepository shipmentRepository;
    private final ShippingEventPublisher eventPublisher;

    public ShippingService(ShipmentRepository shipmentRepository, ShippingEventPublisher eventPublisher) {
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void processPaymentCompleted(PaymentCompletedEvent event) {
        createAndPublishShipment(event.getOrderId(), event.getUserId());
    }

    public void processStockReserved(StockReservedEvent event) {
        createAndPublishShipment(event.getOrderId(), event.getUserId());
    }

    private void createAndPublishShipment(UUID orderId, String userId) {
        Shipment shipment = new Shipment(orderId, userId);
        shipment.setStatus("SHIPPED");
        shipment.setAddress("123 Main Street, City");

        shipmentRepository.save(shipment);
        eventPublisher.publishShipmentCreated(shipment);

        System.out.println("🚚 Shipment Created Successfully for Order: " + orderId);
    }
}