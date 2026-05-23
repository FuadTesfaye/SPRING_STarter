package com.assignment.order.application.usecase;

import com.assignment.order.application.dto.OrderResponse;
import com.assignment.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class GetOrdersUseCase {

    private final OrderRepository repository;

    public GetOrdersUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public List<OrderResponse> execute(UUID userId) {
        return repository.findByUserId(userId).stream().map(OrderResponse::from).toList();
    }
}
