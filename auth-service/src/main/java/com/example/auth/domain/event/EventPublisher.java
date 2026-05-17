// auth-service/src/main/java/com/example/auth/domain/event/EventPublisher.java
package com.example.auth.domain.event;

public interface EventPublisher {
    void publishUserRegistered(UserRegisteredEvent event);
}