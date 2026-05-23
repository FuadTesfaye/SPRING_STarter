package com.assignment.shipping.application.usecase;

import com.assignment.shipping.application.dto.PaymentCompletedEvent;
import com.assignment.shipping.application.dto.StockReservedEvent;
import com.assignment.shipping.domain.event.ShipmentCreated;
import com.assignment.shipping.domain.model.Shipment;
import com.assignment.shipping.domain.repository.ShipmentRepository;
import com.assignment.shipping.infrastructure.messaging.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CreateShipmentUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateShipmentUseCase.class);
    private static final long TTL_SECONDS = 300; // discard orphaned events after 5 min

    private final ShipmentRepository repository;
    private final EventPublisher publisher;
    private final ObjectMapper mapper;

    // Stores typed events keyed by orderId, alongside the arrival timestamp for TTL
    private final ConcurrentHashMap<String, PaymentCompletedEvent> paymentEvents = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, StockReservedEvent>    stockEvents   = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Instant>               arrivedAt     = new ConcurrentHashMap<>();

    public CreateShipmentUseCase(ShipmentRepository repository, EventPublisher publisher, ObjectMapper mapper) {
        this.repository = repository;
        this.publisher  = publisher;
        this.mapper     = mapper;
    }

    public synchronized void onPaymentCompleted(Map<String, Object> raw) {
        PaymentCompletedEvent event = mapper.convertValue(raw, PaymentCompletedEvent.class);
        paymentEvents.put(event.orderId(), event);
        arrivedAt.putIfAbsent(event.orderId(), Instant.now());
        log.info("[SHIPPING] Received payment.completed for order={}", event.orderId());
        tryCreateShipment(event.orderId());
    }

    public synchronized void onStockReserved(Map<String, Object> raw) {
        StockReservedEvent event = mapper.convertValue(raw, StockReservedEvent.class);
        stockEvents.put(event.orderId(), event);
        arrivedAt.putIfAbsent(event.orderId(), Instant.now());
        log.info("[SHIPPING] Received stock.reserved for order={}", event.orderId());
        tryCreateShipment(event.orderId());
    }

    private void tryCreateShipment(String orderId) {
        PaymentCompletedEvent payment = paymentEvents.get(orderId);
        StockReservedEvent    stock   = stockEvents.get(orderId);

        if (payment == null || stock == null) {
            log.info("[SHIPPING] Waiting for both events — order={} (payment={}, stock={})",
                orderId, payment != null, stock != null);
            return;
        }

        if (repository.findByOrderId(UUID.fromString(orderId)).isPresent()) {
            log.info("[SHIPPING] Shipment already exists for order={}", orderId);
            cleanup(orderId);
            return;
        }

        Shipment shipment = Shipment.create(
            UUID.fromString(orderId),
            UUID.fromString(payment.userId()),
            stock.productName(),
            stock.quantity()
        );
        Shipment saved = repository.save(shipment);

        publisher.publish("shipment.created", ShipmentCreated.of(
            saved.getId(), saved.getOrderId(), saved.getUserId(),
            saved.getProductName(), saved.getQuantity(), saved.getTrackingNumber()
        ));

        log.info("[SHIPPING] CREATED — shipmentId={} order={} tracking={}",
            saved.getId(), orderId, saved.getTrackingNumber());

        cleanup(orderId);
    }

    private void cleanup(String orderId) {
        paymentEvents.remove(orderId);
        stockEvents.remove(orderId);
        arrivedAt.remove(orderId);
    }

    /** Evict orphaned events that never received their partner within TTL. */
    @Scheduled(fixedDelay = 60_000)
    public synchronized void evictExpired() {
        Instant cutoff = Instant.now().minusSeconds(TTL_SECONDS);
        arrivedAt.entrySet().stream()
            .filter(e -> e.getValue().isBefore(cutoff))
            .map(Map.Entry::getKey)
            .toList()
            .forEach(orderId -> {
                log.warn("[SHIPPING] Evicting orphaned events for order={} (TTL exceeded)", orderId);
                cleanup(orderId);
            });
    }
}
