package com.ecommerce.auth.domain.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRegisteredEvent {
    private String userId;
    private String email;
    private String username;
    private LocalDateTime timestamp;
}
