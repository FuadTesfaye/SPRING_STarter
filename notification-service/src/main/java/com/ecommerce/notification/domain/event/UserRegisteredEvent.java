package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRegisteredEvent {
    private String userId;
private String email;
private String username;
private java.time.LocalDateTime timestamp;
}
