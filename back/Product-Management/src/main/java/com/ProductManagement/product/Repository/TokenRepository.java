package com.ProductManagement.product.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // Correct method to find a token by the associated user's userId (accessing UserDetails through the 'user' field in Token)
    Optional<Token> findByUser_UserId(Long userId);

    // Find a token by the token string (useful for validation)
    Optional<Token> findByToken(String token);
}
