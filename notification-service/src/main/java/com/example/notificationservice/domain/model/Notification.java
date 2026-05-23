package com.example.notificationservice.domain.model;
import java.time.Instant;
public class Notification {
    private String type; private String payload; private Instant occurredAt;
    public Notification() {}
    public Notification(String t, String p, Instant o) { type=t; payload=p; occurredAt=o; }
    public String getType(){return type;} public void setType(String v){type=v;}
    public String getPayload(){return payload;} public void setPayload(String v){payload=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
