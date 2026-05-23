package com.example.shippingservice.infrastructure.readiness;
import com.example.shippingservice.application.port.OrderReadinessPort;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryOrderReadiness implements OrderReadinessPort {
    private final Map<UUID, Set<String>> state = new ConcurrentHashMap<>();
    @Override public void markPaymentCompleted(UUID id) {
        state.computeIfAbsent(id, k -> ConcurrentHashMap.newKeySet()).add("PAY");
    }
    @Override public void markStockReserved(UUID id) {
        state.computeIfAbsent(id, k -> ConcurrentHashMap.newKeySet()).add("STOCK");
    }
    @Override public boolean isReady(UUID id) {
        Set<String> s = state.get(id);
        return s != null && s.contains("PAY") && s.contains("STOCK");
    }
    @Override public void clear(UUID id) { state.remove(id); }
}
