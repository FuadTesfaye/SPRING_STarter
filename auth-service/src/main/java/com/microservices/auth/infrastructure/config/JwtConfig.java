package com.microservices.auth.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * CONFIGURATION: JwtConfig
 * 
 * WHAT: Loads JWT settings from application.yml
 * WHY: 
 * - Centralized configuration
 * - Environment-specific values
 * - Type-safe access to properties
 * 
 * READS FROM application.yml:
 * jwt:
 *   secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
 *   expiration: 86400000
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    
    private String secret;
    private long expiration;  // in milliseconds
    
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public long getExpiration() {
        return expiration;
    }
    
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
