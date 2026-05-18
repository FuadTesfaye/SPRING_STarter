package com.app.shipping.application.usecases;

import com.app.shipping.application.ports.ShippingEventPublisher;
import com.app.shipping.domain.Shipment;
import com.app.shipping.domain.ShipmentRepository;
import java.util.UUID;

public class CreateShipmentUseCase {
    private final ShipmentRepository shipmentRepository;
    private final ShippingEventPublisher eventPublisher;

    public CreateShipmentUseCase(ShipmentRepository shipmentRepository, ShippingEventPublisher eventPublisher) {
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void handlePaymentCompleted(UUID orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElse(new Shipment(UUID.randomUUID(), orderId, null));
        
        shipment.setPaymentReceived(true);
        processShipment(shipment);
    }

    public void handleStockReserved(UUID orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElse(new Shipment(UUID.randomUUID(), orderId, null));
        
        shipment.setStockReserved(true);
        processShipment(shipment);
    }

    private void processShipment(Shipment shipment) {
        if (shipment.isReady() && shipment.getTrackingNumber() == null) {
            shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            shipmentRepository.save(shipment);
            eventPublisher.publishShipmentCreated(shipment);
        } else {
            shipmentRepository.save(shipment);
        }
    }
}
