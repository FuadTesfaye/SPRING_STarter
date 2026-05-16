package com.example.inventory.application.service;

import com.example.events.OrderCreatedEvent;
import com.example.inventory.application.port.out.InventoryEventPublisher;
import com.example.inventory.application.port.out.ReservationRepository;
import com.example.inventory.application.port.out.StockRepository;
import com.example.inventory.domain.model.Reservation;
import com.example.inventory.domain.model.ReservationStatus;
import com.example.inventory.domain.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for InventoryApplicationService
 * 
 * Tests stock reservation logic including:
 * - Successful stock reservation
 * - Failed stock reservation (insufficient stock)
 * - Event publishing
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Inventory Application Service Tests")
class InventoryApplicationServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private InventoryEventPublisher inventoryEventPublisher;

    private InventoryApplicationService inventoryApplicationService;

    @BeforeEach
    void setUp() {
        inventoryApplicationService = new InventoryApplicationService(
                stockRepository,
                reservationRepository,
                inventoryEventPublisher
        );
    }

    @Test
    @DisplayName("Should reserve stock successfully when stock is available")
    void testReserveStockSuccess() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                1L,
                1L,
                100L,
                2,
                Instant.now()
        );

        Stock availableStock = new Stock(
                1L,
                100L,
                10, // total quantity
                0,  // reserved
                Instant.now(),
                Instant.now()
        );

        Reservation mockReservation = new Reservation(
                1L,
                1L,
                100L,
                2,
                ReservationStatus.RESERVED,
                Instant.now(),
                Instant.now()
        );

        when(stockRepository.findByProductId(100L)).thenReturn(Optional.of(availableStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(availableStock);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        // Act
        inventoryApplicationService.reserveStock(event);

        // Assert
        verify(stockRepository).findByProductId(100L);
        verify(stockRepository).save(any(Stock.class));
        verify(reservationRepository).save(any(Reservation.class));
        verify(inventoryEventPublisher).publishStockReserved(mockReservation);
        verify(inventoryEventPublisher, times(0)).publishStockFailed(any(), any());
    }

    @Test
    @DisplayName("Should fail reservation when stock is insufficient")
    void testReserveStockFailure() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                2L,
                1L,
                100L,
                10, // requesting 10 units
                Instant.now()
        );

        Stock insufficientStock = new Stock(
                1L,
                100L,
                5,  // only 5 units available
                0,
                Instant.now(),
                Instant.now()
        );

        Reservation mockFailedReservation = new Reservation(
                2L,
                2L,
                100L,
                10,
                ReservationStatus.FAILED,
                Instant.now(),
                Instant.now()
        );

        when(stockRepository.findByProductId(100L)).thenReturn(Optional.of(insufficientStock));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockFailedReservation);

        // Act
        inventoryApplicationService.reserveStock(event);

        // Assert
        verify(stockRepository).findByProductId(100L);
        verify(reservationRepository).save(any(Reservation.class));
        verify(inventoryEventPublisher).publishStockFailed(any(), any());
        verify(inventoryEventPublisher, times(0)).publishStockReserved(any());
    }

    @Test
    @DisplayName("Should update stock correctly after reservation")
    void testStockUpdateAfterReservation() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                3L,
                1L,
                100L,
                3,
                Instant.now()
        );

        Stock stock = new Stock(
                1L,
                100L,
                10,
                2, // already 2 reserved
                Instant.now(),
                Instant.now()
        );

        Reservation mockReservation = new Reservation(
                3L,
                3L,
                100L,
                3,
                ReservationStatus.RESERVED,
                Instant.now(),
                Instant.now()
        );

        when(stockRepository.findByProductId(100L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        // Act
        inventoryApplicationService.reserveStock(event);

        // Assert
        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository).save(stockCaptor.capture());

        Stock updatedStock = stockCaptor.getValue();
        assertEquals(5, updatedStock.getReserved()); // 2 + 3 = 5
        assertEquals(5, updatedStock.getAvailable()); // 10 - 5 = 5
    }

    @Test
    @DisplayName("Should publish stock reserved event on success")
    void testEventPublishingOnSuccess() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                4L,
                1L,
                100L,
                1,
                Instant.now()
        );

        Stock stock = new Stock(
                1L,
                100L,
                10,
                0,
                Instant.now(),
                Instant.now()
        );

        Reservation mockReservation = new Reservation(
                4L,
                4L,
                100L,
                1,
                ReservationStatus.RESERVED,
                Instant.now(),
                Instant.now()
        );

        when(stockRepository.findByProductId(100L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        // Act
        inventoryApplicationService.reserveStock(event);

        // Assert
        verify(inventoryEventPublisher, times(1)).publishStockReserved(mockReservation);
    }
}
