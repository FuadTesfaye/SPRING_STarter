package com.school.inventory.application.service;

import com.school.inventory.application.port.EventPublisher;
import com.school.inventory.application.port.StockReservationRepository;
import com.school.inventory.domain.entity.StockReservation;
import com.school.inventory.domain.event.StockFailedEvent;
import com.school.inventory.domain.event.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockReservationRepository reservationRepository;
    private final EventPublisher eventPublisher;
    private final Random random = new Random();

    public void checkAndReserve(Long orderId, String studentId, String feeType) {
        log.info("[INVENTORY] Checking stock for order: {} feeType: {}", orderId, feeType);

        StockReservation reservation = new StockReservation(orderId, studentId, feeType);

        // Simulate 90% availability for campus products
        boolean available = random.nextInt(10) < 9;

        if (available) {
            reservation.setStatus("RESERVED");
            reservationRepository.save(reservation);

            StockReservedEvent event = new StockReservedEvent(orderId, studentId, feeType);
            eventPublisher.publish("stock.reserved", event);
            log.info("[INVENTORY] Stock RESERVED for order: {}", orderId);
        } else {
            reservation.setStatus("FAILED");
            reservationRepository.save(reservation);

            StockFailedEvent event = new StockFailedEvent(
                orderId, studentId, feeType, "Fee slot not available at this time"
            );
            eventPublisher.publish("stock.failed", event);
            log.warn("[INVENTORY] Stock FAILED for order: {}", orderId);
        }
    }
}
