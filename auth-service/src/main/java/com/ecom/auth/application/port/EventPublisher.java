package com.ecom.auth.application.port;

public interface EventPublisher {
    void publishUserRegistered(Object event);
}
