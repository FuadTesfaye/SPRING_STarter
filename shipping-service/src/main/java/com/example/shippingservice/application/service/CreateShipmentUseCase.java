package com.example.shippingservice.application.service;
import com.example.shippingservice.application.port.*;
import com.example.shippingservice.domain.event.ShipmentCreatedEvent;
import com.example.shippingservice.domain.model.Shipment;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import java.time.Instant; import java.util.UUID;

@Service
public class CreateShipmentUseCase {
    private static final Logger log = LoggerFactory.getLogger(CreateShipmentUseCase.class);
    private final ShipmentRepositoryPort repo;
    private final EventPublisherPort publisher;
    private final OrderReadinessPort readiness;
    public CreateShipmentUseCase(ShipmentRepositoryPort r, EventPublisherPort p, OrderReadinessPort rd) {
        repo=r; publisher=p; readiness=rd;
    }

    public synchronized void onPaymentCompleted(UUID orderId) {
        readiness.markPaymentCompleted(orderId);
        tryShip(orderId);
    }
    public synchronized void onStockReserved(UUID orderId) {
        readiness.markStockReserved(orderId);
        tryShip(orderId);
    }

    private void tryShip(UUID orderId) {
        if (!readiness.isReady(orderId)) return;
        if (repo.findByOrderId(orderId).isPresent()) return;
        Shipment s = new Shipment(UUID.randomUUID(), orderId, "CREATED", Instant.now());
        repo.save(s);
        readiness.clear(orderId);
        log.info("Shipment created for order {}", orderId);
        publisher.publish("shipment.created",
            new ShipmentCreatedEvent(s.getId(), orderId, Instant.now()));
    }
}
