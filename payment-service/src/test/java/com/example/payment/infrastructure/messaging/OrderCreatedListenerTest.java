package com.example.payment.infrastructure.messaging;

import com.example.events.OrderCreatedEvent;
import com.example.payment.application.service.PaymentApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for OrderCreatedListener
 * 
 * Tests message listening and event handling
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Order Created Listener Tests")
class OrderCreatedListenerTest {

    @Mock
    private PaymentApplicationService paymentApplicationService;

    private OrderCreatedListener orderCreatedListener;

    @BeforeEach
    void setUp() {
        orderCreatedListener = new OrderCreatedListener(paymentApplicationService);
    }

    @Test
    @DisplayName("Should call payment service when order created event is received")
    void testHandleOrderCreated() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                1L,
                1L,
                100L,
                2,
                Instant.now()
        );

        // Act
        orderCreatedListener.handleOrderCreated(event);

        // Assert
        verify(paymentApplicationService, times(1)).processPayment(event);
    }

    @Test
    @DisplayName("Should handle multiple order created events")
    void testHandleMultipleOrderCreatedEvents() {
        // Arrange
        OrderCreatedEvent event1 = new OrderCreatedEvent(1L, 1L, 100L, 2, Instant.now());
        OrderCreatedEvent event2 = new OrderCreatedEvent(2L, 1L, 101L, 3, Instant.now());
        OrderCreatedEvent event3 = new OrderCreatedEvent(3L, 1L, 102L, 1, Instant.now());

        // Act
        orderCreatedListener.handleOrderCreated(event1);
        orderCreatedListener.handleOrderCreated(event2);
        orderCreatedListener.handleOrderCreated(event3);

        // Assert
        verify(paymentApplicationService, times(3)).processPayment(event1);
    }
}
