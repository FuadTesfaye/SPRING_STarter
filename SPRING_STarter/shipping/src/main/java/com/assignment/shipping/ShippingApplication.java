package com.assignment.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShippingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingApplication.class, args);
        System.out.println("🚚 Shipping Service running on http://localhost:8085");
        System.out.println("📝 H2 Console: http://localhost:8085/h2-console");
    }
}