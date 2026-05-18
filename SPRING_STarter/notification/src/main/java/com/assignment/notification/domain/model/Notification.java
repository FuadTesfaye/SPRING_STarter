package com.assignment.notification.domain.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String service;
    private String event;
    private String details;
    private LocalDateTime receivedAt;
    
    public Notification() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    
    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}
