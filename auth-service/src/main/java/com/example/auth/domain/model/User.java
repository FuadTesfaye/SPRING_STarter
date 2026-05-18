package com.example.auth.domain.model;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
}
