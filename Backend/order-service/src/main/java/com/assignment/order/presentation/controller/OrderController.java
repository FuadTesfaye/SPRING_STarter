package com.assignment.order.presentation.controller;

import com.assignment.order.application.dto.CreateOrderRequest;
import com.assignment.order.application.dto.OrderResponse;
import com.assignment.order.application.usecase.CreateOrderUseCase;
import com.assignment.order.application.usecase.GetEventsUseCase;
import com.assignment.order.application.usecase.GetOrdersUseCase;
import com.assignment.order.infrastructure.catalogue.EventCatalogue;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;
    private final GetEventsUseCase getEventsUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           GetOrdersUseCase getOrdersUseCase,
                           GetEventsUseCase getEventsUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
        this.getEventsUseCase = getEventsUseCase;
    }

    // ── Event catalogue endpoints (served here so frontend needs no separate event-service) ──

    @GetMapping("/events")
    public ResponseEntity<List<EventCatalogue.EventInfo>> listEvents(
        @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(getEventsUseCase.execute(category));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventCatalogue.EventInfo> getEvent(@PathVariable String id) {
        return getEventsUseCase.execute(null).stream()
            .filter(e -> e.id().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ── Order/booking endpoints ──────────────────────────────────────────────

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(
        @Valid @RequestBody CreateOrderRequest request,
        @RequestHeader(value = "X-User-Id",    defaultValue = "00000000-0000-0000-0000-000000000001") String userId,
        @RequestHeader(value = "X-User-Email", required = false) String userEmail,
        @RequestHeader(value = "X-User-Name",  required = false) String fullName
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createOrderUseCase.execute(UUID.fromString(userId), request, userEmail, fullName));
    }

    /** Also expose as /bookings to match the frontend bookingApi URL. */
    @PostMapping("/bookings")
    public ResponseEntity<OrderResponse> createBooking(
        @Valid @RequestBody CreateOrderRequest request,
        @RequestHeader(value = "X-User-Id",    defaultValue = "00000000-0000-0000-0000-000000000001") String userId,
        @RequestHeader(value = "X-User-Email", required = false) String userEmail,
        @RequestHeader(value = "X-User-Name",  required = false) String fullName
    ) {
        return createOrder(request, userId, userEmail, fullName);
    }

    @GetMapping("/orders/my")
    public ResponseEntity<List<OrderResponse>> myOrders(
        @RequestHeader(value = "X-User-Id", defaultValue = "00000000-0000-0000-0000-000000000001") String userId
    ) {
        return ResponseEntity.ok(getOrdersUseCase.execute(UUID.fromString(userId)));
    }

    /** Also expose as /bookings/my for frontend bookingApi.myBookings(). */
    @GetMapping("/bookings/my")
    public ResponseEntity<List<OrderResponse>> myBookings(
        @RequestHeader(value = "X-User-Id", defaultValue = "00000000-0000-0000-0000-000000000001") String userId
    ) {
        return myOrders(userId);
    }

    @GetMapping("/orders/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "order-service"));
    }
}
