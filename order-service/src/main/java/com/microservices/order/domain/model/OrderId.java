package com.microservices.order.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class OrderId {
    private final UUID value;
    
    private OrderId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        this.value = value;
    }
    
    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }
    
    public static OrderId fromString(String id) {
        return new OrderId(UUID.fromString(id));
    }
    
    public UUID getValue() { return value; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId other = (OrderId) o;
        return value.equals(other.value);
    }
    
    @Override
    public int hashCode() { return Objects.hash(value); }
    
    @Override
    public String toString() { return value.toString(); }
}
