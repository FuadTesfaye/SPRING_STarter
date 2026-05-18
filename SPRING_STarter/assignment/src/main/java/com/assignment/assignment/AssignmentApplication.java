package com.assignment.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
        System.out.println("🔐 Auth Service running on http://localhost:8081");
        System.out.println("📝 H2 Console: http://localhost:8081/h2-console");
    }
}