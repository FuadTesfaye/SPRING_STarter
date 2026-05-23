package com.assignment.order.application.usecase;

import com.assignment.order.application.dto.CreateOrderRequest;
import com.assignment.order.application.dto.OrderResponse;
import com.assignment.order.domain.event.OrderCreated;
import com.assignment.order.domain.model.Order;
import com.assignment.order.domain.repository.OrderRepository;
import com.assignment.order.infrastructure.catalogue.EventCatalogue;
import com.assignment.order.infrastructure.messaging.EventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CreateOrderUseCase {

    private final OrderRepository repository;
    private final EventPublisher publisher;
    private final EventCatalogue catalogue;

    public CreateOrderUseCase(OrderRepository repository, EventPublisher publisher, EventCatalogue catalogue) {
        this.repository = repository;
        this.publisher = publisher;
        this.catalogue = catalogue;
    }

    public OrderResponse execute(UUID userId, CreateOrderRequest request, String userEmail, String fullName) {
        EventCatalogue.EventInfo event = catalogue.findById(request.eventId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + request.eventId()));

        Order order = Order.create(userId, request.eventId(), event.title(), request.quantity(), event.ticketPrice());
        Order saved = repository.save(order);

        publisher.publish("order.created", OrderCreated.of(
            saved.getId(), saved.getUserId(), saved.getProductName(),
            saved.getQuantity(), saved.getPrice(),
            userEmail != null ? userEmail : "",
            fullName  != null ? fullName  : ""
        ));

        return OrderResponse.from(saved);
    }
}
