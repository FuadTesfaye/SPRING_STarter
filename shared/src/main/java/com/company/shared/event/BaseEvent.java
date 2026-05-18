package com.company.shared.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEvent {
    private String eventId;
    private String eventType;
    private LocalDateTime occurredAt;
    private String correlationId;
    private String source;

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredAt = LocalDateTime.now();
    }

    public BaseEvent(String eventType, String correlationId, String source) {
        this();
        this.eventType = eventType;
        this.correlationId = correlationId;
        this.source = source;
    }

    public String getEventId() { return eventId; }
    public String getEventType() { return eventType; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
