// package com.ProductManagement.product.Services;

// import java.time.LocalDateTime;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.ProductManagement.product.Entity.Token;
// import com.ProductManagement.product.Entity.UserDetails;
// import com.ProductManagement.product.Repository.TokenRepository;
// import com.ProductManagement.product.Repository.UsersRepository;
// import com.ProductManagement.product.dto.LoginRequest;
// import com.ProductManagement.product.dto.TokenResponseDTO;

// import jakarta.transaction.Transactional;

// @Service
// @Transactional
// public class AuthService {

//     private final PasswordEncoder passwordEncoder;

//     @Autowired
//     public AuthService(PasswordEncoder passwordEncoder) {
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Autowired
//     private UsersRepository usersRepository; // Use UsersRepository for UserDetails

//     @Autowired
//     private TokenRepository tokenRepository;

//     @Autowired
//     private JwtService jwtService;

//     public TokenResponseDTO authenticateAndGenerateToken(LoginRequest loginRequest) {
//         String username = loginRequest.getUsername();
//         String password = loginRequest.getPassword();

//         // Fetch user details from the UsersRepository (which works with UserDetails)
//         Optional<UserDetails> userOptional = usersRepository.findByUserName(username);

//         if (userOptional.isPresent()) {
//             UserDetails user = userOptional.get();

//             // Verify password using BCrypt
//             if (passwordEncoder.matches(password, user.getPassword())) {
//                 // Generate JWT token
//                 String token = jwtService.generateToken(username);

//                 // Set expiration time (e.g., 1 hour from now)
//                 LocalDateTime now = LocalDateTime.now();
//                 LocalDateTime expirationTime = now.plusHours(1);

//                 // Create and save token entity
//                 Token tokenEntity = new Token();
//                 tokenEntity.setUser(user);  // Set the UserDetails entity to Token
//                 tokenEntity.setUsername(user.getUserName());  // Set the username (no password needed)
//                 tokenEntity.setToken(token);  // Set the generated token
//                 tokenEntity.setGeneratedAt(now);  // Set the time when token was generated
//                 tokenEntity.setExpiredAt(expirationTime);  // Set the expiration time for the token

//                 tokenRepository.save(tokenEntity);  // Persist the token entity

//                 // Prepare TokenResponseDTO with the token and expiration time
//                 TokenResponseDTO tokenResponse = new TokenResponseDTO();
//                 tokenResponse.setToken(token);
//                 tokenResponse.setExpirationTime(expirationTime);

//                 return tokenResponse;  // Return the token response with the JWT token and expiration time
//             } else {
//                 throw new RuntimeException("Invalid password");  // Invalid password, throw exception
//             }
//         } else {
//             throw new RuntimeException("Invalid username");  // User not found, throw exception
//         }
//     }
// }



package com.ProductManagement.product.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ProductManagement.product.Entity.Token;
import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Repository.TokenRepository;
import com.ProductManagement.product.Repository.UsersRepository;
import com.ProductManagement.product.dto.LoginRequest;
import com.ProductManagement.product.dto.TokenResponseDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UsersRepository usersRepository; // Use UsersRepository for UserDetails

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    

    public TokenResponseDTO authenticateAndGenerateToken(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        

        // Fetch user details from UsersRepository
        Optional<UserDetails> userOptional = usersRepository.findByUserName(username);

        if (userOptional.isPresent()) {
            UserDetails user = userOptional.get();

            // Verify password using BCrypt
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Fetch userRoleId
                Long userRoleId = user.getUserRoleMaster().getUserRoleId();

                // Generate JWT token with role ID
                String token = jwtService.generateToken(username, userRoleId);

                // Set expiration time (e.g., 1 hour from now)
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime expirationTime = now.plusHours(1);

                // Create and save token entity
                Token tokenEntity = new Token();
                tokenEntity.setUser(user);  
                tokenEntity.setUsername(user.getUserName());  
                tokenEntity.setToken(token);  
                tokenEntity.setGeneratedAt(now);  
                tokenEntity.setExpiredAt(expirationTime);  

                tokenRepository.save(tokenEntity);  

                // Prepare TokenResponseDTO
                TokenResponseDTO tokenResponse = new TokenResponseDTO();
                tokenResponse.setToken(token);
                tokenResponse.setExpirationTime(expirationTime);
                tokenResponse.setUserRoleId(userRoleId); // Send userRoleId in response

                return tokenResponse;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Invalid username");
        }
    }
}
