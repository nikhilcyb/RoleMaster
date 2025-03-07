package com.ProductManagement.product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.UserDetails;
@Repository
public interface UsersRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUserName(String username);
    Optional<UserDetails> findByEmail(String email);
    Optional<UserDetails> findByApiKey(String apiKey);

    List<UserDetails> findByUserNameContainingIgnoreCase(String username);
}
