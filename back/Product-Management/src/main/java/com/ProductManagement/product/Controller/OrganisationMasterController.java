// package com.ProductManagement.product.Controller;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.regex.Pattern;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.ProductManagement.product.Entity.OrganisationMaster;
// import com.ProductManagement.product.Services.OrganisationService;

// import jakarta.persistence.EntityNotFoundException;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/organizations")
// public class OrganisationMasterController {

//     @Autowired
//     private OrganisationService organizationService;

//     // Utility method to validate token
//     private void validateToken(String token) {
//         if (token == null || token.isBlank()) {
//             throw new IllegalArgumentException("Invalid Authorization Token");
//         }
//         // Add your actual token validation logic here (e.g., JWT verification)
//     }

//     @GetMapping
//     public ResponseEntity<List<OrganisationMaster>> getAllOrganizations(
//             @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             List<OrganisationMaster> organizations = organizationService.getAllOrganizations();
//             if (organizations.isEmpty()) {
//                 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//             }
//             return ResponseEntity.ok(organizations);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<OrganisationMaster> getOrganizationById(
//             @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             OrganisationMaster organization = organizationService.getOrganizationById(id);
//             if (organization == null) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//             }
//             return ResponseEntity.ok(organization);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @PostMapping
//     public ResponseEntity<?> createOrganization(
//             @RequestBody OrganisationMaster organization,
//             @RequestHeader("Authorization") String authorizationHeader) {
                
//                 System.out.println("POST /organizations/ endpoint hit");

//         validateToken(authorizationHeader);
//         try {
//             Map<String, String> errors = new HashMap<>();

//             if (organization == null) {
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                         .body(Map.of("error", "Request body cannot be null"));
//             }

//             if (organization.getOrganizationName() == null || organization.getOrganizationName().isBlank()) {
//                 errors.put("organizationName", "Organization name is required");
//             }

//             if (!isValidEmail(organization.getEmail())) {
//                 errors.put("email", "Invalid email format");
//             }

//             if (organization.getContactNo() != null && !organization.getContactNo().matches("\\d{10}")) {
//                 errors.put("contactNo", "Contact number must be a 10-digit number");
//             }

//             if (organization.getDomainName() == null || organization.getDomainName().isBlank()) {
//                 errors.put("domainName", "Domain name is required");
//             }

//             if (organization.getOrgLimit() == null || organization.getOrgLimit() < 0) {
//                 errors.put("orgLimit", "Organization limit must be zero or greater");
//             }

//             if (organization.getIsEnabled() == null) {
//                 errors.put("isEnabled", "IsEnabled must be provided as true or false");
//             }

//             if (!errors.isEmpty()) {
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//             }

//             OrganisationMaster createdOrganization = organizationService.createOrganization(organization);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganization);

//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(Map.of("error", "An unexpected error occurred"));
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<OrganisationMaster> updateOrganization(
//             @PathVariable Long id,
//             @RequestBody OrganisationMaster organizationDetails,
//             @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             if (organizationDetails == null) {
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//             }

//             OrganisationMaster updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
//             if (updatedOrganization == null) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//             }
//             return ResponseEntity.ok(updatedOrganization);

//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteOrganization(
//             @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             organizationService.deleteOrganization(id);
//             return ResponseEntity.noContent().build();
//         } catch (EntityNotFoundException e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//         }
//     }

//     // Utility method for email validation
//     private boolean isValidEmail(String email) {
//         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//         return Pattern.compile(emailRegex).matcher(email).matches();
//     }
// }




// package com.ProductManagement.product.Controller;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.regex.Pattern;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.ProductManagement.product.Entity.OrganisationMaster;
// import com.ProductManagement.product.Services.OrganisationService;

// import jakarta.persistence.EntityNotFoundException;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/organizations")
// public class OrganisationMasterController {

//     @Autowired
//     private OrganisationService organizationService;

//     private void validateToken(String authorizationHeader) {
//         if (authorizationHeader == null || authorizationHeader.isBlank()) {
//             throw new IllegalArgumentException("Missing or invalid Authorization token");
//         }
//         // Implement actual token validation logic here (e.g., JWT verification)
//     }

//     @GetMapping
//     public ResponseEntity<List<OrganisationMaster>> getAllOrganizations(
//         @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             List<OrganisationMaster> organizations = organizationService.getAllOrganizations();
//             return organizations.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
//                     : ResponseEntity.ok(organizations);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<OrganisationMaster> getOrganizationById(
//             @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             OrganisationMaster organization = organizationService.getOrganizationById(id);
//             return organization == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
//                     : ResponseEntity.ok(organization);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @PostMapping
//     public ResponseEntity<?> createOrganization(
//             @RequestBody OrganisationMaster organization,
//             @RequestHeader("Authorization")  String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             Map<String, String> errors = validateOrganization(organization);
//             if (!errors.isEmpty()) {
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//             }
//             OrganisationMaster createdOrganization = organizationService.createOrganization(organization);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganization);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(Map.of("error", "An unexpected error occurred"));
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<OrganisationMaster> updateOrganization(
//             @PathVariable Long id,
//             @RequestBody OrganisationMaster organizationDetails,
//             @RequestHeader("Authorization")  String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             if (organizationDetails == null) {
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//             }
//             OrganisationMaster updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
//             return updatedOrganization == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
//                     : ResponseEntity.ok(updatedOrganization);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteOrganization(
//             @PathVariable Long id, @RequestHeader("Authorization")  String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             organizationService.deleteOrganization(id);
//             return ResponseEntity.noContent().build();
//         } catch (EntityNotFoundException e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//         }
//     }

//     private Map<String, String> validateOrganization(OrganisationMaster organization) {
//         Map<String, String> errors = new HashMap<>();
//         if (organization.getOrganizationName() == null || organization.getOrganizationName().isBlank()) {
//             errors.put("organizationName", "Organization name is required");
//         }
//         if (!isValidEmail(organization.getEmail())) {
//             errors.put("email", "Invalid email format");
//         }
//         if (organization.getContactNo() != null && !organization.getContactNo().matches("\\d{10}")) {
//             errors.put("contactNo", "Contact number must be a 10-digit number");
//         }
//         if (organization.getDomainName() == null || organization.getDomainName().isBlank()) {
//             errors.put("domainName", "Domain name is required");
//         }
//         if (organization.getOrgLimit() == null || organization.getOrgLimit() < 0) {
//             errors.put("orgLimit", "Organization limit must be zero or greater");
//         }
//         if (organization.getIsEnabled() == null) {
//             errors.put("isEnabled", "IsEnabled must be provided as true or false");
//         }
//         return errors;
//     }

//     private boolean isValidEmail(String email) {
//         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//         return Pattern.compile(emailRegex).matcher(email).matches();
//     }
// }


package com.ProductManagement.product.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProductManagement.product.Entity.OrganisationMaster;
import com.ProductManagement.product.ErrorHandle.BadRequestException;
import com.ProductManagement.product.Services.JwtService;
import com.ProductManagement.product.Services.OrganisationService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/organizations")
public class OrganisationMasterController {

    @Autowired
    private OrganisationService organizationService;

	@Autowired
    private JwtService jwtService;

    // Utility method to validate token
      // Validate the JWT token
    private void validateToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization header is missing or invalid.");
        }

        String token = authorizationHeader.replace("Bearer ", "");


        if (!jwtService.isValidToken(token)) {
            throw new BadRequestException("Invalid or expired token.");
        }else{
            //jwt decode  call service to decode the jwt ge
        }
    } public ResponseEntity<String> getHeaderValue(
            @RequestHeader("Authorization") String authorizationHeader) {
        // Print the value of the Authorization header
        System.out.println("Authorization Header: " + authorizationHeader);

        // Return a response with the header value
        return ResponseEntity.ok("Received Authorization Header: " + authorizationHeader);
    }

    @GetMapping
    public ResponseEntity<List<OrganisationMaster>> getAllOrganizations(
            @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        try {
            List<OrganisationMaster> organizations = organizationService.getAllOrganizations();
			
            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(organizations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationMaster> getOrganizationById(
            @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        try {
            OrganisationMaster organization = organizationService.getOrganizationById(id);
            if (organization == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(organization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(
            @RequestBody OrganisationMaster organization,
            @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        try {
            Map<String, String> errors = new HashMap<>();

            if (organization == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Request body cannot be null"));
            }

            if (organization.getOrganizationName() == null || organization.getOrganizationName().isBlank()) {
                errors.put("organizationName", "Organization name is required");
            }

            if (!isValidEmail(organization.getEmail())) {
                errors.put("email", "Invalid email format");
            }

            if (organization.getContactNo() != null && !organization.getContactNo().matches("\\d{10}")) {
                errors.put("contactNo", "Contact number must be a 10-digit number");
            }

            if (organization.getDomainName() == null || organization.getDomainName().isBlank()) {
                errors.put("domainName", "Domain name is required");
            }

            if (organization.getOrgLimit() == null || organization.getOrgLimit() < 0) {
                errors.put("orgLimit", "Organization limit must be zero or greater");
            }

            if (organization.getIsEnabled() == null) {
                errors.put("isEnabled", "IsEnabled must be provided as true or false");
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            OrganisationMaster createdOrganization = organizationService.createOrganization(organization);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganization);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganisationMaster> updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganisationMaster organizationDetails,
            @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        try {
            if (organizationDetails == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            OrganisationMaster updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
            if (updatedOrganization == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(updatedOrganization);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(
            @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        try {
            organizationService.deleteOrganization(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Utility method for email validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}