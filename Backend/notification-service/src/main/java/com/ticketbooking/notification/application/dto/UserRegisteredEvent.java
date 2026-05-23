package com.ticketbooking.notification.application.dto;
public record UserRegisteredEvent(String userId, String email, String fullName) {}
