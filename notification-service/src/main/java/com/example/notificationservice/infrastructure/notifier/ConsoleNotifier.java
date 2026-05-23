package com.example.notificationservice.infrastructure.notifier;
import com.example.notificationservice.application.port.NotifierPort;
import com.example.notificationservice.domain.model.Notification;
import org.slf4j.*;
import org.springframework.stereotype.Component;
@Component
public class ConsoleNotifier implements NotifierPort {
    private static final Logger log = LoggerFactory.getLogger(ConsoleNotifier.class);
    @Override public void notify(Notification n) {
        log.info("[NOTIFICATION] type={} payload={}", n.getType(), n.getPayload());
    }
}
