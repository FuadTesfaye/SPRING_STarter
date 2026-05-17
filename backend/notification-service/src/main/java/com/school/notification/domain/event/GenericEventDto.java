package com.school.notification.domain.event;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Flexible DTO to capture any incoming event for logging
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericEventDto {
    private Long orderId;
    private String studentId;
    private String studentName;
    private String feeType;
    private String status;
    private String reference;
    private String trackingNumber;
    private String reason;
}
