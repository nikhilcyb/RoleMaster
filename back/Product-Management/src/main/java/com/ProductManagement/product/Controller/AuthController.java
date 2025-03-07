package com.ProductManagement.product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProductManagement.product.Services.AuthService;
import com.ProductManagement.product.dto.LoginRequest;
import com.ProductManagement.product.dto.TokenResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            TokenResponseDTO tokenResponse = authService.authenticateAndGenerateToken(loginRequest);

            // Set the token in HTTP-only cookies
            Cookie jwtCookie = new Cookie("jwtToken", tokenResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // 1 hour expiration
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Logout functionality: invalidate the JWT token by clearing the cookie
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Create a cookie with the same name and set its maxAge to 0 to invalidate it
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire the cookie immediately
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}