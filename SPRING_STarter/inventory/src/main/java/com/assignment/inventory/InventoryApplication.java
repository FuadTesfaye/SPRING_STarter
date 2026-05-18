package com.assignment.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
        System.out.println("📦 Inventory Service running on http://localhost:8084");
        System.out.println("📝 H2 Console: http://localhost:8084/h2-console");
    }
}