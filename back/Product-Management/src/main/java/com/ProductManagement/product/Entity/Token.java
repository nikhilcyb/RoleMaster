package com.ProductManagement.product.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Updated the foreign key relationship with UserDetails
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)  // Ensuring it references userId in UserDetails
    private UserDetails user;  // This now correctly refers to UserDetails

    private String username;  // New field for username (optional)
    private String token;
    private LocalDateTime generatedAt;
    private LocalDateTime expiredAt;

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetails getUser() {  // This getter correctly returns the UserDetails entity
        return user;
    }

    public void setUser(UserDetails user) {  // This setter correctly accepts a UserDetails entity
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }
}
