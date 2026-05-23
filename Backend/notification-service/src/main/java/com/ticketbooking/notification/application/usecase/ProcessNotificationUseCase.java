package com.ticketbooking.notification.application.usecase;

import com.ticketbooking.notification.application.dto.*;
import com.ticketbooking.notification.domain.model.EmailTemplate;
import com.ticketbooking.notification.domain.model.NotificationMessage;
import com.ticketbooking.notification.infrastructure.email.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessNotificationUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessNotificationUseCase.class);

    private final ObjectMapper mapper;
    private final EmailService emailService;

    public ProcessNotificationUseCase(ObjectMapper mapper, EmailService emailService) {
        this.mapper = mapper;
        this.emailService = emailService;
    }

    public void execute(String routingKey, Map<String, Object> raw) {
        NotificationMessage notification = buildNotificationAndSendEmail(routingKey, raw);
        log.info("[NOTIFICATION] [{}] {}", notification.getRoutingKey(), notification.getMessage());
    }

    private NotificationMessage buildNotificationAndSendEmail(String routingKey, Map<String, Object> raw) {
        String message = switch (routingKey) {
            case "user.registered" -> {
                UserRegisteredEvent e = mapper.convertValue(raw, UserRegisteredEvent.class);
                emailService.send(
                    e.email(),
                    "Welcome to TicketHub!",
                    EmailTemplate.welcome(e.fullName(), e.email())
                );
                yield String.format("Welcome %s! Your account (%s) has been created.", e.fullName(), e.email());
            }
            case "order.created" -> {
                OrderCreatedEvent e = mapper.convertValue(raw, OrderCreatedEvent.class);
                if (e.userEmail() != null && !e.userEmail().isBlank()) {
                    String totalStr = e.price().multiply(java.math.BigDecimal.valueOf(e.quantity()))
                                       .setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
                    emailService.send(
                        e.userEmail(),
                        "Your TicketHub Booking Confirmation — " + e.productName(),
                        EmailTemplate.orderConfirmation(
                            e.fullName() != null && !e.fullName().isBlank() ? e.fullName() : e.userEmail(),
                            e.orderId(), e.productName(), e.quantity(), totalStr
                        )
                    );
                } else {
                    log.info("[EMAIL] No email for userId={} — skipping booking confirmation", e.userId());
                }
                yield String.format("Order %s placed: %d x '%s' @ $%s each.",
                    e.orderId(), e.quantity(), e.productName(), e.price());
            }
            case "payment.completed" -> {
                PaymentCompletedEvent e = mapper.convertValue(raw, PaymentCompletedEvent.class);
                log.info("[EMAIL] Payment confirmed for userId={} orderId={}", e.userId(), e.orderId());
                yield String.format("Payment %s COMPLETED for order %s — $%s charged.",
                    e.paymentId(), e.orderId(), e.amount());
            }
            case "payment.failed" -> {
                PaymentFailedEvent e = mapper.convertValue(raw, PaymentFailedEvent.class);
                log.info("[EMAIL] Payment failed for userId={} orderId={}", e.userId(), e.orderId());
                yield String.format("Payment %s FAILED for order %s — reason: %s",
                    e.paymentId(), e.orderId(), e.reason());
            }
            case "stock.reserved" -> {
                StockReservedEvent e = mapper.convertValue(raw, StockReservedEvent.class);
                yield String.format("Stock RESERVED: %d x '%s' for order %s.",
                    e.quantity(), e.productName(), e.orderId());
            }
            case "stock.failed" -> {
                StockFailedEvent e = mapper.convertValue(raw, StockFailedEvent.class);
                yield String.format("Stock FAILED for order %s — product '%s': %s",
                    e.orderId(), e.productName(), e.reason());
            }
            case "shipment.created" -> {
                ShipmentCreatedEvent e = mapper.convertValue(raw, ShipmentCreatedEvent.class);
                log.info("[EMAIL] Shipment notification for userId={} tracking={}", e.userId(), e.trackingNumber());
                yield String.format("Shipment CREATED for order %s — tracking: %s",
                    e.orderId(), e.trackingNumber());
            }
            default -> String.format("Unknown event [%s]: %s", routingKey, raw);
        };

        return NotificationMessage.of(routingKey, message);
    }
}
