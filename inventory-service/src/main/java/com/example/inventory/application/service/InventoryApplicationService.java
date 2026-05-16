package com.example.inventory.application.service;

import com.example.events.OrderCreatedEvent;
import com.example.inventory.application.dto.ReservationResponse;
import com.example.inventory.application.exception.InsufficientStockException;
import com.example.inventory.application.exception.ProductNotFoundException;
import com.example.inventory.application.port.out.InventoryEventPublisher;
import com.example.inventory.application.port.out.ReservationRepository;
import com.example.inventory.application.port.out.StockRepository;
import com.example.inventory.domain.model.Reservation;
import com.example.inventory.domain.model.ReservationStatus;
import com.example.inventory.domain.model.Stock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class InventoryApplicationService {

    private final StockRepository stockRepository;
    private final ReservationRepository reservationRepository;
    private final InventoryEventPublisher inventoryEventPublisher;

    public InventoryApplicationService(
            StockRepository stockRepository,
            ReservationRepository reservationRepository,
            InventoryEventPublisher inventoryEventPublisher
    ) {
        this.stockRepository = stockRepository;
        this.reservationRepository = reservationRepository;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    @Transactional
    public void reserveStock(OrderCreatedEvent event) {
        Stock stock = stockRepository.findByProductId(event.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + event.productId()));

        if (!stock.canReserve(event.quantity())) {
            Reservation failedReservation = new Reservation(
                    null,
                    event.orderId(),
                    event.productId(),
                    event.quantity(),
                    ReservationStatus.FAILED,
                    Instant.now(),
                    Instant.now()
            );
            reservationRepository.save(failedReservation);
            publishStockFailedAsync(event.orderId(), "Insufficient stock. Available: " + stock.getAvailable() + ", Requested: " + event.quantity());
            return;
        }

        Stock updatedStock = new Stock(
                stock.getId(),
                stock.getProductId(),
                stock.getQuantity(),
                stock.getReserved() + event.quantity(),
                stock.getCreatedAt(),
                Instant.now()
        );
        stockRepository.save(updatedStock);

        Reservation reservation = new Reservation(
                null,
                event.orderId(),
                event.productId(),
                event.quantity(),
                ReservationStatus.RESERVED,
                Instant.now(),
                Instant.now()
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        publishStockReservedAsync(savedReservation);
    }

    @Async
    private void publishStockReservedAsync(Reservation reservation) {
        try {
            inventoryEventPublisher.publishStockReserved(reservation);
            System.out.println("📊 Stock reserved event published");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish stock reserved event: " + e.getMessage());
        }
    }

    @Async
    private void publishStockFailedAsync(Long orderId, String reason) {
        try {
            inventoryEventPublisher.publishStockFailed(orderId, reason);
            System.out.println("❌ Stock failed event published");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish stock failed event: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + reservationId));
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getOrderId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }
}
