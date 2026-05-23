package com.example.notificationservice.application.port;
import com.example.notificationservice.domain.model.Notification;
public interface NotifierPort { void notify(Notification n); }
