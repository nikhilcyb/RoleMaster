


package com.ProductManagement.product.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProductManagement.product.Entity.UserRoleMaster;
import com.ProductManagement.product.ErrorHandle.BadRequestException;
import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
import com.ProductManagement.product.Services.JwtService;
import com.ProductManagement.product.Services.UserRoleMasterServices;

@RestController
@RequestMapping("/roles")
public class UserRoleMasterController {

    @Autowired
    private UserRoleMasterServices userRoleMasterService;

    @Autowired
    private JwtService jwtService; // Added for token validation

    // Utility method to validate JWT token from request header
    private void validateToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization header is missing or invalid.");
        }

        String token = authorizationHeader.replace("Bearer ", "");

        if (!jwtService.isValidToken(token)) {
            throw new BadRequestException("Invalid or expired token.");
        }
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<?> getAllRoles(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            List<UserRoleMaster> roles = userRoleMasterService.getAllRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching roles: " + e.getMessage());
        }
    }

    // Create a role with privileges
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody UserRoleMaster role, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            UserRoleMaster createdRole = userRoleMasterService.createRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating role: " + e.getMessage());
        }
    }

    // Get role by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            UserRoleMaster role = userRoleMasterService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching role: " + e.getMessage());
        }
    }

    // Update role by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody UserRoleMaster updatedRole, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            UserRoleMaster role = userRoleMasterService.updateRole(id, updatedRole);
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage()); // 400 Bad Request for invalid input
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found with ID: " + id); // 404 Not Found for resource not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating role: " + e.getMessage()); // 500 Internal Server Error
        }
    }

    // Delete role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            userRoleMasterService.deleteRole(id);
            return ResponseEntity.ok().body("Role deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: " + e.getMessage()); // 404 Not Found if role does not exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting role: " + e.getMessage()); // 500 Internal Server Error
        }
    }
}
