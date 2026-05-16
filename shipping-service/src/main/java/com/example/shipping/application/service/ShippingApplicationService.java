package com.example.shipping.application.service;

import com.example.events.PaymentCompletedEvent;
import com.example.events.StockReservedEvent;
import com.example.shipping.application.dto.ShipmentResponse;
import com.example.shipping.application.port.out.ShipmentRepository;
import com.example.shipping.application.port.out.ShippingEventPublisher;
import com.example.shipping.domain.model.Shipment;
import com.example.shipping.domain.model.ShipmentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingApplicationService {

    private final ShipmentRepository shipmentRepository;
    private final ShippingEventPublisher shippingEventPublisher;
    private final Map<Long, OrderReadiness> orderReadiness = new HashMap<>();

    public ShippingApplicationService(
            ShipmentRepository shipmentRepository,
            ShippingEventPublisher shippingEventPublisher
    ) {
        this.shipmentRepository = shipmentRepository;
        this.shippingEventPublisher = shippingEventPublisher;
    }

    @Transactional
    public void handleStockReserved(StockReservedEvent event) {
        OrderReadiness readiness = orderReadiness.computeIfAbsent(
                event.orderId(),
                k -> new OrderReadiness()
        );
        readiness.stockReserved = true;
        checkAndCreateShipment(event.orderId());
    }

    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        OrderReadiness readiness = orderReadiness.computeIfAbsent(
                event.orderId(),
                k -> new OrderReadiness()
        );
        readiness.paymentCompleted = true;
        checkAndCreateShipment(event.orderId());
    }

    private void checkAndCreateShipment(Long orderId) {
        OrderReadiness readiness = orderReadiness.get(orderId);
        if (readiness != null && readiness.isReady()) {
            createShipment(orderId);
            orderReadiness.remove(orderId);
        }
    }

    @Transactional
    public void createShipment(Long orderId) {
        Shipment shipment = new Shipment(
                null,
                orderId,
                "SHIP-" + orderId + "-" + Instant.now().toEpochMilli(),
                ShipmentStatus.CREATED,
                Instant.now(),
                Instant.now()
        );

        Shipment savedShipment = shipmentRepository.save(shipment);
        publishShipmentCreatedAsync(savedShipment);
    }

    @org.springframework.scheduling.annotation.Async
    private void publishShipmentCreatedAsync(Shipment shipment) {
        try {
            shippingEventPublisher.publishShipmentCreated(shipment);
            System.out.println("🚚 Shipment created event published");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish shipment created event: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ShipmentResponse getShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));
    }

    @Transactional(readOnly = true)
    public ShipmentResponse getShipmentByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Shipment not found for order: " + orderId));
    }

    private ShipmentResponse toResponse(Shipment shipment) {
        return new ShipmentResponse(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getShipmentReference(),
                shipment.getStatus(),
                shipment.getCreatedAt()
        );
    }

    private static class OrderReadiness {
        boolean stockReserved = false;
        boolean paymentCompleted = false;

        boolean isReady() {
            return stockReserved && paymentCompleted;
        }
    }
}
