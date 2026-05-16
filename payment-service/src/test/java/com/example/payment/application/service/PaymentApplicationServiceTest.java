package com.example.payment.application.service;

import com.example.events.OrderCreatedEvent;
import com.example.payment.application.port.out.PaymentEventPublisher;
import com.example.payment.application.port.out.PaymentRepository;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.model.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for PaymentApplicationService
 * 
 * Tests payment processing logic including:
 * - Successful payment processing
 * - Failed payment processing
 * - Event publishing
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Payment Application Service Tests")
class PaymentApplicationServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentEventPublisher paymentEventPublisher;

    private PaymentApplicationService paymentApplicationService;

    @BeforeEach
    void setUp() {
        paymentApplicationService = new PaymentApplicationService(
                paymentRepository,
                paymentEventPublisher
        );
    }

    @Test
    @DisplayName("Should process payment successfully when amount is below threshold")
    void testProcessPaymentSuccess() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                1L,
                1L,
                100L,
                2,
                Instant.now()
        );

        Payment mockPayment = new Payment(
                1L,
                1L,
                new BigDecimal("240.00"),
                PaymentStatus.COMPLETED,
                "PAY-1-1715857200000",
                null,
                Instant.now()
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // Act
        paymentApplicationService.processPayment(event);

        // Assert
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());

        Payment savedPayment = paymentCaptor.getValue();
        assertEquals(PaymentStatus.COMPLETED, savedPayment.getStatus());
        assertEquals(new BigDecimal("240.00"), savedPayment.getAmount());

        verify(paymentEventPublisher).publishPaymentCompleted(mockPayment);
        verify(paymentEventPublisher, times(0)).publishPaymentFailed(any());
    }

    @Test
    @DisplayName("Should fail payment when amount exceeds threshold")
    void testProcessPaymentFailure() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                2L,
                1L,
                100L,
                5, // 5 × $120 = $600 > $500 threshold
                Instant.now()
        );

        Payment mockPayment = new Payment(
                2L,
                2L,
                new BigDecimal("600.00"),
                PaymentStatus.FAILED,
                null,
                "Mock payment rejected because total exceeds 500.00",
                Instant.now()
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // Act
        paymentApplicationService.processPayment(event);

        // Assert
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());

        Payment savedPayment = paymentCaptor.getValue();
        assertEquals(PaymentStatus.FAILED, savedPayment.getStatus());
        assertEquals(new BigDecimal("600.00"), savedPayment.getAmount());
        assertNotNull(savedPayment.getFailureReason());

        verify(paymentEventPublisher).publishPaymentFailed(mockPayment);
        verify(paymentEventPublisher, times(0)).publishPaymentCompleted(any());
    }

    @Test
    @DisplayName("Should calculate amount correctly based on quantity")
    void testAmountCalculation() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                3L,
                1L,
                100L,
                3, // 3 × $120 = $360
                Instant.now()
        );

        Payment mockPayment = new Payment(
                3L,
                3L,
                new BigDecimal("360.00"),
                PaymentStatus.COMPLETED,
                "PAY-3-1715857200000",
                null,
                Instant.now()
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // Act
        paymentApplicationService.processPayment(event);

        // Assert
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());

        Payment savedPayment = paymentCaptor.getValue();
        assertEquals(new BigDecimal("360.00"), savedPayment.getAmount());
    }

    @Test
    @DisplayName("Should publish event after successful payment")
    void testEventPublishingOnSuccess() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                4L,
                1L,
                100L,
                1,
                Instant.now()
        );

        Payment mockPayment = new Payment(
                4L,
                4L,
                new BigDecimal("120.00"),
                PaymentStatus.COMPLETED,
                "PAY-4-1715857200000",
                null,
                Instant.now()
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // Act
        paymentApplicationService.processPayment(event);

        // Assert
        verify(paymentEventPublisher, times(1)).publishPaymentCompleted(mockPayment);
    }
}
