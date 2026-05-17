package com.example.notification.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationApplicationService {

    public void deliver(String routingKey, String payload) {
        log.info("[NOTIFICATION] routingKey={} payload={}", routingKey, payload);
    }
}
