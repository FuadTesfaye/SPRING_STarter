package com.ecommerce.order.application.usecase;

import com.ecommerce.order.application.dto.OrderRequest;
import com.ecommerce.order.application.dto.OrderResponse;

public interface CreateOrderUseCase {
    OrderResponse createOrder(OrderRequest request);
}
