// package com.ProductManagement.product.Services;

// import java.util.Date;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.SignatureException;
// import io.jsonwebtoken.UnsupportedJwtException;

// @Service
// public class JwtService {

//     @Value("${jwt.secret}")
//     private String secretKey;

//     @Value("${jwt.expiration}")
//     private long expirationTime;

//     // Generate JWT Token using the username
//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration time
//                 .signWith(SignatureAlgorithm.HS256, secretKey) // Sign with HMAC SHA256
//                 .compact();
//     }

//     // Validate JWT Token with more specific error handling
//     public boolean validateToken(String token) {
//         try {
//             // Parsing the JWT with the provided secret key to verify its validity
//             Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//             return true;
//         } catch (ExpiredJwtException e) {
//             System.out.println("Token expired");
//         } catch (UnsupportedJwtException e) {
//             System.out.println("Unsupported JWT token");
//         } catch (MalformedJwtException e) {
//             System.out.println("Malformed JWT token");
//         } catch (SignatureException e) {
//             System.out.println("Invalid JWT signature");
//         } catch (Exception e) {
//             System.out.println("Invalid JWT token");
//         }
//         return false;
//     }

//     // Extract username from the JWT token
//     public String getUsernameFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//         return claims.getSubject(); // Get the subject (username)
//     }

//     // Extract the expiration date from the JWT token
//     public Date getExpirationDateFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//         return claims.getExpiration(); // Get the expiration date
//     }

//     // Check if the token is expired
//     public boolean isTokenExpired(String token) {
//         Date expiration = getExpirationDateFromToken(token);
//         return expiration.before(new Date()); // Compare expiration with the current date
//     }

//     // Extract the user ID from the JWT token (if you store the user ID in the token)
//     public String getUserIdFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//         return claims.getId(); // You can set the user ID when generating the token
//     }

//     // Full token validation (including checking expiration)
//     public boolean isValidToken(String token) {
//         return validateToken(token) && !isTokenExpired(token); // Validate and check expiration
//     }
// }



package com.ProductManagement.product.Services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // Generate JWT Token including username and userRoleId
    public String generateToken(String username, Long userRoleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userRoleId", userRoleId); // ✅ Store userRoleId in the token
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign with HMAC SHA256
                .compact();
    }

    // Validate JWT Token with detailed error handling
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT token");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
        } catch (Exception e) {
            System.out.println("Invalid JWT token");
        }
        return false;
    }

    // Extract username from the JWT token
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract userRoleId from the JWT token
    public Long getUserRoleIdFromToken(String token) {
        return extractClaims(token).get("userRoleId", Long.class); // ✅ Extract userRoleId
    }

    // Extract expiration date from the JWT token
    public Date getExpirationDateFromToken(String token) {
        return extractClaims(token).getExpiration();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    // Extract claims from the JWT token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Full token validation (including checking expiration)
    public boolean isValidToken(String token) {
        return validateToken(token) && !isTokenExpired(token);
    }
}
