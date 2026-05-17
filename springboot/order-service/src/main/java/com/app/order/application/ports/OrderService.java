package com.app.order.application.ports;

import com.app.order.application.dto.OrderRequest;
import com.app.order.application.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
}
