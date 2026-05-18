package com.assignment.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
        System.out.println("💳 Payment Service running on http://localhost:8083");
        System.out.println("📝 H2 Console: http://localhost:8083/h2-console");
    }
}