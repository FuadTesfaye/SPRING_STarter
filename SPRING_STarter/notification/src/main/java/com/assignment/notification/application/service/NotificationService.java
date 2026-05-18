package com.assignment.notification.application.service;

import com.assignment.notification.application.dto.LogRequest;
import com.assignment.notification.domain.model.Notification;
import com.assignment.notification.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    public void logEvent(LogRequest request) {
        Notification notification = new Notification();
        notification.setService(request.getService());
        notification.setEvent(request.getEvent());
        notification.setDetails(request.getData() != null ? request.getData().toString() : "{}");
        notification.setReceivedAt(LocalDateTime.now());
        
        notificationRepository.save(notification);
        
        // Also print to console for visibility
        System.out.println("=========================================");
        System.out.println("📢 NOTIFICATION RECEIVED:");
        System.out.println("   Service: " + request.getService());
        System.out.println("   Event: " + request.getEvent());
        System.out.println("   Data: " + request.getData());
        System.out.println("   Time: " + LocalDateTime.now());
        System.out.println("=========================================");
    }
}