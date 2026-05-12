package com.microservices.notificationservice.domain.port;

public interface NotificationPort {

    void send(String message);
}