package com.ProductManagement.product.dto;

import java.time.LocalDateTime;

public class TokenResponseDTO {
    private String token;
    private LocalDateTime expirationTime;
    private Long userRoleId; // Add this field

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getUserRoleId() {  // Getter for userRoleId
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {  // Setter for userRoleId
        this.userRoleId = userRoleId;
    }
}
