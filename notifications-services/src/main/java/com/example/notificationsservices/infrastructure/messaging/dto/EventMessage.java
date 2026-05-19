package com.example.notificationsservices.infrastructure.messaging.dto;

import lombok.Data;

@Data
public class EventMessage {
    private String type;
    private String recipient;
    private String message;
}