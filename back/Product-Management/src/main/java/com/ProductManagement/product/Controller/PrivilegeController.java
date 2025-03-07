

// package com.ProductManagement.product.Controller;

// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.ProductManagement.product.Entity.Privilege;
// import com.ProductManagement.product.Services.PrivilegeService;
// import com.ProductManagement.product.Services.JwtService;
// import com.ProductManagement.product.ErrorHandle.BadRequestException;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/privileges")
// public class PrivilegeController {

//     @Autowired
//     private PrivilegeService privilegeService;

//     @Autowired
//     private JwtService jwtService; // Added for token validation

//     // Utility method to validate JWT token from request header
//     private void validateToken(String authorizationHeader) {
//         if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//             throw new BadRequestException("Authorization header is missing or invalid.");
//         }

//         String token = authorizationHeader.replace("Bearer ", "");

//         if (!jwtService.isValidToken(token)) {
//             throw new BadRequestException("Invalid or expired token.");
//         }
//     }

//     // Endpoint to get all privileges
//     @GetMapping
//     public List<Privilege> getAllPrivileges(@RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         return privilegeService.getAllPrivileges();
//     }

//     // Endpoint to create a new privilege
//     @PostMapping
//     public Privilege createPrivilege(@RequestBody Privilege privilege, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         return privilegeService.createPrivilege(privilege);
//     }

//     // Endpoint to get a privilege by its ID
//     @GetMapping("/{id}")
//     public Privilege getPrivilegeById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         return privilegeService.getPrivilegeById(id);
//     }

//     // Endpoint to delete a privilege
//     @DeleteMapping("/{id}")
//     public void deletePrivilege(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         privilegeService.deletePrivilege(id);
//     }

//     // Endpoint to update an existing privilege
//     @PutMapping("/{id}")
//     public Privilege updatePrivilege(@PathVariable Long id, @RequestBody Privilege updatedPrivilege, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         return privilegeService.updatePrivilege(id, updatedPrivilege);
//     }
// }


package com.ProductManagement.product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ProductManagement.product.Entity.Privilege;
import com.ProductManagement.product.Services.PrivilegeService;
import com.ProductManagement.product.Services.JwtService;
import com.ProductManagement.product.ErrorHandle.BadRequestException;
import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

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

    // Endpoint to get all privileges
    @GetMapping
    public ResponseEntity<?> getAllPrivileges(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            List<Privilege> privileges = privilegeService.getAllPrivileges();
            return ResponseEntity.ok(privileges);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching privileges: " + e.getMessage());
        }
    }

    // Endpoint to create a new privilege
    @PostMapping
    public ResponseEntity<?> createPrivilege(@RequestBody Privilege privilege, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            Privilege createdPrivilege = privilegeService.createPrivilege(privilege);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPrivilege);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating privilege: " + e.getMessage());
        }
    }

    // Endpoint to get a privilege by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrivilegeById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            Privilege privilege = privilegeService.getPrivilegeById(id);
            if (privilege == null) {
                throw new ResourceNotFoundException("Privilege not found with ID: " + id);
            }
            return ResponseEntity.ok(privilege);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching privilege: " + e.getMessage());
        }
    }

    // Endpoint to delete a privilege
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrivilege(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            privilegeService.deletePrivilege(id);
            return ResponseEntity.ok().body("Privilege deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Privilege not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting privilege: " + e.getMessage());
        }
    }

    // Endpoint to update an existing privilege
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrivilege(@PathVariable Long id, @RequestBody Privilege updatedPrivilege, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            validateToken(authorizationHeader);
            Privilege privilege = privilegeService.updatePrivilege(id, updatedPrivilege);
            if (privilege == null) {
                throw new ResourceNotFoundException("Privilege not found with ID: " + id);
            }
            return ResponseEntity.ok(privilege);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Privilege not found with ID: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating privilege: " + e.getMessage());
        }
    }
}
