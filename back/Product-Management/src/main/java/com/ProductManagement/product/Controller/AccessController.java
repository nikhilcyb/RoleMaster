// package com.ProductManagement.product.Controller;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.ProductManagement.product.Entity.OrganisationMaster;
// import com.ProductManagement.product.Entity.UserDetails;
// import com.ProductManagement.product.Entity.UserOrganizationMapping;
// import com.ProductManagement.product.Repository.OrganizationRepository;
// import com.ProductManagement.product.Repository.UserOrganizationMappingRepository;
// import com.ProductManagement.product.Repository.UsersRepository;
// import com.ProductManagement.product.Services.AccessService;


// @RestController
// @RequestMapping("/api/access")
// public class AccessController {

//     @Autowired
//     private UsersRepository userRepository;

//     @Autowired
//     private OrganizationRepository organizationRepository;

//     @Autowired
//     private AccessService accessService;


//     @Autowired
//     private UserOrganizationMappingRepository userOrganizationMappingRepository; // Autowire the repository

//     // New API to resolve userId from username
//     @GetMapping("/resolve-user-id")
//     public ResponseEntity<Long> resolveUserId(@RequestParam String username) {
//         Optional<UserDetails> user = userRepository.findByUserName(username);
//         if (user.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
//         }
//         return ResponseEntity.ok(user.get().getUserId()); // Return the userId
//     }

//     // Check if user has access to the organization
//     @GetMapping("/check")
//     public ResponseEntity<String> checkAccess(@RequestParam Long userId, @RequestParam Long organizationId) {
//         // Validate if user exists
//         Optional<UserDetails> user = userRepository.findById(userId);
//         if (user.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//         }

//         // Validate if organization exists
//         Optional<OrganisationMaster> organization = organizationRepository.findById(organizationId);
//         if (organization.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
//         }

//         // If both are valid, proceed to access check
//         String result = accessService.checkAccess(userId, organizationId);
//         return ResponseEntity.ok(result);
//     }

//     // Check if user is already connected to the organization
//     @GetMapping("/check-connected")
//     public ResponseEntity<String> checkIfAlreadyConnected(@RequestParam Long userId, @RequestParam Long organizationId) {
//         // Validate if user exists
//         Optional<UserDetails> user = userRepository.findById(userId);
//         if (user.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//         }

//         // Validate if organization exists
//         Optional<OrganisationMaster> organization = organizationRepository.findById(organizationId);
//         if (organization.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
//         }

//         // If both are valid, check if already connected
//         String result = accessService.checkIfAlreadyConnected(userId, organizationId);
//         return ResponseEntity.ok(result);
//     }

//     // API to fetch all mappings (User - Organization relationships)
//     @GetMapping("/mappings")
//     public ResponseEntity<List<UserOrganizationMapping>> getAllMappings() {
//         List<UserOrganizationMapping> mappings = accessService.getAllMappings();
//         return ResponseEntity.ok(mappings);
//     }


//     @GetMapping("/search-username")
//     public ResponseEntity<List<UserDetails>> searchUsernames(@RequestParam String username) {
//         List<UserDetails> users = userRepository.findByUserNameContainingIgnoreCase(username);
//         return ResponseEntity.ok(users);
//     }


//     @DeleteMapping("/mappings/{id}")
//     public ResponseEntity<String> deleteMapping(@PathVariable Long id) {
//         System.out.println("Deleting mapping with ID: " + id);
//         if (userOrganizationMappingRepository.existsById(id)) {
//             System.out.println("Mapping found. Deleting...");
//             userOrganizationMappingRepository.deleteById(id);
//             return ResponseEntity.ok("Mapping deleted successfully.");
//         } else {
//             System.out.println("Mapping not found for ID: " + id);
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found.");
//         }
//     }



// }















package com.ProductManagement.product.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ProductManagement.product.Entity.OrganisationMaster;
import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Entity.UserOrganizationMapping;
import com.ProductManagement.product.Repository.OrganizationRepository;
import com.ProductManagement.product.Repository.UserOrganizationMappingRepository;
import com.ProductManagement.product.Repository.UsersRepository;
import com.ProductManagement.product.Services.AccessService;
import com.ProductManagement.product.Services.JwtService;
import com.ProductManagement.product.ErrorHandle.BadRequestException;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AccessService accessService;

    @Autowired
    private UserOrganizationMappingRepository userOrganizationMappingRepository; // Autowire the repository

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

    // New API to resolve userId from username
    @GetMapping("/resolve-user-id")
    public ResponseEntity<Long> resolveUserId(@RequestParam String username, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        Optional<UserDetails> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
        }
        return ResponseEntity.ok(user.get().getUserId()); // Return the userId
    }

    // Check if user has access to the organization
    @GetMapping("/check")
    public ResponseEntity<String> checkAccess(@RequestParam Long userId, @RequestParam Long organizationId, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        // Validate if user exists
        Optional<UserDetails> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Validate if organization exists
        Optional<OrganisationMaster> organization = organizationRepository.findById(organizationId);
        if (organization.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
        }

        // If both are valid, proceed to access check
        String result = accessService.checkAccess(userId, organizationId);
        return ResponseEntity.ok(result);
    }

    // Check if user is already connected to the organization
    @GetMapping("/check-connected")
    public ResponseEntity<String> checkIfAlreadyConnected(@RequestParam Long userId, @RequestParam Long organizationId, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        // Validate if user exists
        Optional<UserDetails> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Validate if organization exists
        Optional<OrganisationMaster> organization = organizationRepository.findById(organizationId);
        if (organization.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
        }

        // If both are valid, check if already connected
        String result = accessService.checkIfAlreadyConnected(userId, organizationId);
        return ResponseEntity.ok(result);
    }

    // API to fetch all mappings (User - Organization relationships)
    @GetMapping("/mappings")
    public ResponseEntity<List<UserOrganizationMapping>> getAllMappings(@RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        List<UserOrganizationMapping> mappings = accessService.getAllMappings();
        return ResponseEntity.ok(mappings);
    }

    @GetMapping("/search-username")
    public ResponseEntity<List<UserDetails>> searchUsernames(@RequestParam String username, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        List<UserDetails> users = userRepository.findByUserNameContainingIgnoreCase(username);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/mappings/{id}")
    public ResponseEntity<String> deleteMapping(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        System.out.println("Deleting mapping with ID: " + id);
        if (userOrganizationMappingRepository.existsById(id)) {
            System.out.println("Mapping found. Deleting...");
            userOrganizationMappingRepository.deleteById(id);
            return ResponseEntity.ok("Mapping deleted successfully.");
        } else {
            System.out.println("Mapping not found for ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found.");
        }
    }
}





