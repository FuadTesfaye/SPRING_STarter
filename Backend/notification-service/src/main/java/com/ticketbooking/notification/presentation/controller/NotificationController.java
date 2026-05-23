package com.ticketbooking.notification.presentation.controller;

import com.ticketbooking.notification.domain.model.EmailTemplate;
import com.ticketbooking.notification.domain.model.InAppNotification;
import com.ticketbooking.notification.domain.model.ShopOrder;
import com.ticketbooking.notification.infrastructure.email.EmailService;
import com.ticketbooking.notification.infrastructure.store.NotificationStore;
import com.ticketbooking.notification.infrastructure.store.ShopOrderStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private static final String ADMIN_KEY = "philemondan32@gmail.com:Dantab@4040";

    private final EmailService emailService;
    private final ShopOrderStore orderStore;
    private final NotificationStore notificationStore;

    public NotificationController(EmailService emailService,
                                   ShopOrderStore orderStore,
                                   NotificationStore notificationStore) {
        this.emailService = emailService;
        this.orderStore = orderStore;
        this.notificationStore = notificationStore;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "notification-service"));
    }

    // ── Shop Orders (admin manages these) ────────────────────────

    @PostMapping("/admin/orders")
    public ResponseEntity<ShopOrder> createOrder(
            @RequestBody ShopOrderRequest req,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        ShopOrder order = new ShopOrder(
            req.orderId() != null ? req.orderId() : "SHOP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            req.productName(), req.quantity(), req.totalAmount(),
            req.email(), req.fullName(), "PENDING", Instant.now()
        );
        return ResponseEntity.ok(orderStore.save(order));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<List<ShopOrder>> listOrders(
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        return ResponseEntity.ok(orderStore.findAll());
    }

    @PostMapping("/admin/orders/{orderId}/deliver")
    public ResponseEntity<ShopOrder> markDelivered(
            @PathVariable String orderId,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        ShopOrder order = orderStore.markDelivered(orderId);

        // Send delivery email if we have an email address
        if (order.email() != null && !order.email().isBlank()) {
            emailService.send(
                order.email(),
                "Your order has been delivered! ✅",
                EmailTemplate.itemDelivered(order.fullName(), order.orderId(),
                    order.productName(), order.quantity(), order.totalAmount())
            );
            // Store in-app notification for the user
            notificationStore.add(
                order.email(),
                "Order Delivered!",
                "Your order of " + order.quantity() + "x " + order.productName() + " (ID: " + order.orderId() + ") has been delivered."
            );
        }

        return ResponseEntity.ok(order);
    }

    // ── In-app notifications for the frontend ────────────────────

    @GetMapping("/inbox")
    public ResponseEntity<List<InAppNotification>> inbox(
            @RequestParam String email) {
        return ResponseEntity.ok(notificationStore.findByEmail(email));
    }

    @GetMapping("/inbox/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(
            @RequestParam String email) {
        return ResponseEntity.ok(Map.of("count", notificationStore.countUnread(email)));
    }

    @PostMapping("/inbox/mark-read")
    public ResponseEntity<Void> markRead(@RequestParam String email) {
        notificationStore.markRead(email);
        return ResponseEntity.ok().build();
    }

    // ── Auth ─────────────────────────────────────────────────────

    private void requireAdmin(String key) {
        if (!ADMIN_KEY.equals(key)) throw new SecurityException("Unauthorized");
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, String>> handleUnauth(SecurityException ex) {
        return ResponseEntity.status(403).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    public record ShopOrderRequest(
        String orderId,
        String productName,
        int quantity,
        double totalAmount,
        String email,
        String fullName
    ) {}
}
