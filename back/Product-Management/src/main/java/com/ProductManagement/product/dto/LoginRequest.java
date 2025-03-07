package com.ProductManagement.product.dto;

public class LoginRequest {
    
    private String username;  // Username for authentication
    private String password;  // Password for authentication
    
    // Getter and Setter for Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}