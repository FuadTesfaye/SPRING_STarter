package com.assignment.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("📦 Order Service running on http://localhost:8082");
        System.out.println("📝 H2 Console: http://localhost:8082/h2-console");
    }
}