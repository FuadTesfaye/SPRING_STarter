package com.ecom.shipping.application.service;

import com.ecom.shipping.application.dto.ShipmentCreatedEvent;
import com.ecom.shipping.application.port.EventPublisher;
import com.ecom.shipping.domain.model.Shipment;
import com.ecom.shipping.domain.model.ShippingCorrelation;
import com.ecom.shipping.domain.repository.ShipmentRepository;
import com.ecom.shipping.domain.repository.ShippingCorrelationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShipmentCoordinator {
    private final ShippingCorrelationRepository correlationRepository;
    private final ShipmentRepository shipmentRepository;
    private final EventPublisher eventPublisher;

    public ShipmentCoordinator(ShippingCorrelationRepository correlationRepository, 
                               ShipmentRepository shipmentRepository, 
                               EventPublisher eventPublisher) {
        this.correlationRepository = correlationRepository;
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void handlePaymentCompleted(UUID orderId) {
        ShippingCorrelation correlation = getOrCreateCorrelation(orderId);
        correlation.setPaymentCompleted(true);
        processCorrelation(correlation);
    }

    public void handleStockReserved(UUID orderId) {
        ShippingCorrelation correlation = getOrCreateCorrelation(orderId);
        correlation.setStockReserved(true);
        processCorrelation(correlation);
    }

    private void processCorrelation(ShippingCorrelation correlation) {
        if (correlation.isReadyForShipping()) {
            createShipment(correlation.getOrderId());
            // Optionally delete correlation to clean up
        } else {
            correlationRepository.save(correlation);
        }
    }

    private void createShipment(UUID orderId) {
        Shipment shipment = new Shipment(
                UUID.randomUUID(),
                orderId,
                "SHIPPED",
                "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                LocalDateTime.now()
        );

        shipmentRepository.save(shipment);

        eventPublisher.publishShipmentCreated(new ShipmentCreatedEvent(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getTrackingNumber()
        ));
    }

    private ShippingCorrelation getOrCreateCorrelation(UUID orderId) {
        return correlationRepository.findByOrderId(orderId)
                .orElse(new ShippingCorrelation(orderId, false, false));
    }
}
