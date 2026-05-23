package com.ticketbooking.notification.infrastructure.store;

import com.ticketbooking.notification.domain.model.ShopOrder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShopOrderStore {

    private final Map<String, ShopOrder> orders = new ConcurrentHashMap<>();

    public ShopOrder save(ShopOrder order) {
        orders.put(order.orderId(), order);
        return order;
    }

    public List<ShopOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Optional<ShopOrder> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public ShopOrder markDelivered(String orderId) {
        ShopOrder existing = orders.get(orderId);
        if (existing == null) throw new IllegalArgumentException("Order not found: " + orderId);
        ShopOrder updated = new ShopOrder(
            existing.orderId(), existing.productName(), existing.quantity(),
            existing.totalAmount(), existing.email(), existing.fullName(),
            "DELIVERED", existing.placedAt()
        );
        orders.put(orderId, updated);
        return updated;
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public void seedDemo() {
        if (!orders.isEmpty()) return;
        save(new ShopOrder("SHOP-DEMO001", "Pro Microphone XLR", 1, 139.98, "", "Demo User", "PENDING", Instant.now().minusSeconds(3600)));
        save(new ShopOrder("SHOP-DEMO002", "Official NBA Basketball", 2, 99.98, "", "Demo User 2", "PENDING", Instant.now().minusSeconds(7200)));
    }
}
