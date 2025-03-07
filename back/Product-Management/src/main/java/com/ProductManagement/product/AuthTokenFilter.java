// package com.ProductManagement.product;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.Jwts;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class AuthTokenFilter extends OncePerRequestFilter {

//     @Value("${jwt.secret}")
//     private String secretKey;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         // First, try to get the JWT token from cookies
//         String token = getTokenFromCookies(request.getCookies());

//         // If token not found in cookies, check Authorization header
//         if (token == null) {
//             String authorizationHeader = request.getHeader("Authorization");

//             if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                 token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//             }
//         }

//         // If no token is found, return an error or redirect
//         if (token == null) {
//             response.sendRedirect("/login"); // Redirect to login page
//             return; // Stop further processing
//         }

//         // If token is found, validate it
//         try {
//             Claims claims = Jwts.parserBuilder()
//                     .setSigningKey(secretKey) // Set the secret key for JWT parsing
//                     .build()
//                     .parseClaimsJws(token)
//                     .getBody();

//             String username = claims.getSubject();

//             if (username != null) {
//                 // Create an authentication object and set it in the security context
//                 UsernamePasswordAuthenticationToken authentication =
//                         new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }

//         } catch (ExpiredJwtException e) {
//             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
//             return; // Stop further processing
//         } catch (JwtException e) {
//             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//             return; // Stop further processing
//         } catch (Exception e) {
//             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error processing token: " + e.getMessage());
//             return; // Stop further processing
//         }

//         // Continue with the filter chain if the token is valid
//         filterChain.doFilter(request, response);
//     }

//     /**
//      * Helper method to retrieve the JWT token from cookies.
//      *
//      * @param cookies array of cookies from the request
//      * @return the JWT token if found, or null if not found
//      */
//     private String getTokenFromCookies(Cookie[] cookies) {
//         if (cookies != null) {
//             Optional<Cookie> jwtCookie = Arrays.stream(cookies)
//                     .filter(cookie -> "jwt".equals(cookie.getName())) // Assuming cookie name is 'jwt'
//                     .findFirst();

//             return jwtCookie.map(Cookie::getValue).orElse(null);
//         }
//         return null;
//     }
// }
