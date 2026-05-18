package com.assignment.notification.application.dto;


import java.util.Map;

public class LogRequest {
    private String service;
    private String event;
    private Map<String, Object> data;
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }
    
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}
