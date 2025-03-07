// // package com.ProductManagement.product.Controller;

// // import java.util.List;
// // import java.util.regex.Pattern;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.CrossOrigin;
// // import org.springframework.web.bind.annotation.DeleteMapping;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.PutMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// // import org.springframework.web.bind.annotation.RequestHeader;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RestController;

// // import com.ProductManagement.product.Entity.UserDetails;
// // import com.ProductManagement.product.Entity.UserRoleMaster;
// // import com.ProductManagement.product.ErrorHandle.BadRequestException;
// // import com.ProductManagement.product.ErrorHandle.EmptyRequestBodyException;
// // import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
// // import com.ProductManagement.product.Services.JwtService;
// // import com.ProductManagement.product.Services.UserDetailsService;
// // import com.ProductManagement.product.Services.UserRoleMasterServices;

// // @CrossOrigin(origins = "http://localhost:3000")
// // @RestController
// // @RequestMapping("/users")
// // public class UserDetailsController {

// //     @Autowired
// //     private UserDetailsService usersService;

// //     @Autowired
// //     private UserRoleMasterServices userRoleMasterService;

// //     @Autowired
// //     private JwtService jwtService;

// //     // Validate the JWT token
// //     private void validateToken(String authorizationHeader) {
// //         if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
// //             throw new BadRequestException("Authorization header is missing or invalid.");
// //         }

// //         String token = authorizationHeader.replace("Bearer ", "");

// //         if (!jwtService.isValidToken(token)) {
// //             throw new BadRequestException("Invalid or expired token.");
// //         }
// //     } public ResponseEntity<String> getHeaderValue(
// //             @RequestHeader("Authorization") String authorizationHeader) {
// //         // Print the value of the Authorization header
// //         System.out.println("Authorization Header: " + authorizationHeader);

// //         // Return a response with the header value
// //         return ResponseEntity.ok("Received Authorization Header: " + authorizationHeader);
// //     }

// //     @GetMapping
// //     public List<UserDetails> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
// //         validateToken(authorizationHeader);
// //         return usersService.getAllUsers();
// //     }

// //     @PostMapping
// //     public String createUser(@RequestBody UserDetails user, @RequestHeader("Authorization") String authorizationHeader) {
// //         validateToken(authorizationHeader);

// //         if (user == null || user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
// //             throw new EmptyRequestBodyException("Request body is empty or missing required fields.");
// //         }

// //         if (!isValidEmail(user.getEmail())) {
// //             throw new BadRequestException("Invalid email format: " + user.getEmail());
// //         }

// //         if (user.getUserRoleMaster() == null || user.getUserRoleMaster().getUserRoleId() == null) {
// //             throw new BadRequestException("User role ID is required.");
// //         }

// //         UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
// //         if (userRole == null) {
// //             throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
// //         }

// //         user.setUserRoleMaster(userRole);
// //         usersService.createUser(user);
// //         return "User created successfully.";
// //     }

// //     @PutMapping("/{id}")
// //     public UserDetails updateUser(@PathVariable Long id, @RequestBody UserDetails user,
// //             @RequestHeader("Authorization") String authorizationHeader) {
// //         validateToken(authorizationHeader);

// //         if (user == null || (user.getUserName() == null && user.getEmail() == null && user.getPassword() == null)) {
// //             throw new EmptyRequestBodyException("Request body is empty. Please provide user details.");
// //         }

// //         if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
// //             throw new BadRequestException("Invalid email format: " + user.getEmail());
// //         }

// //         UserDetails existingUser = usersService.getUserById(id);
// //         if (existingUser == null) {
// //             throw new ResourceNotFoundException("User not found with ID: " + id);
// //         }

// //         if (user.getUserRoleMaster() != null && user.getUserRoleMaster().getUserRoleId() != null) {
// //             UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
// //             if (userRole != null) {
// //                 user.setUserRoleMaster(userRole);
// //             } else {
// //                 throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
// //             }
// //         }

// //         user.setUserId(id);
// //         return usersService.updateUser(id, user);
// //     }

// //     @GetMapping("/{id}")
// //     public UserDetails getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
// //         validateToken(authorizationHeader);

// //         try {
// //             return usersService.getUserById(id);
// //         } catch (ResourceNotFoundException e) {
// //             throw new ResourceNotFoundException("User not found with ID: " + id);
// //         } catch (Exception e) {
// //             throw new RuntimeException("An unexpected error occurred while fetching user with ID: " + id, e);
// //         }
// //     }

// //     @DeleteMapping("/{id}")
// //     public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
// //         validateToken(authorizationHeader);
// //         usersService.deleteUser(id);
// //     }

// //     private boolean isValidEmail(String email) {
// //         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
// //         return Pattern.compile(emailRegex).matcher(email).matches();
// //     }
// // }



// // package com.ProductManagement.product.Controller;

// // import java.util.ArrayList;
// // import java.util.HashSet;

// // // package com.Product.userdetails;

// // import java.util.List;
// // import java.util.Set;
// // import java.util.regex.Pattern;
// // import java.util.stream.Collectors;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.web.bind.annotation.CrossOrigin;
// // import org.springframework.web.bind.annotation.DeleteMapping;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.PutMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RestController;

// // import com.ProductManagement.product.Entity.Privilege;
// // import com.ProductManagement.product.Entity.UserDetails;
// // import com.ProductManagement.product.Entity.UserRoleMaster;
// // import com.ProductManagement.product.ErrorHandle.BadRequestException;
// // import com.ProductManagement.product.ErrorHandle.EmptyRequestBodyException;
// // import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
// // import com.ProductManagement.product.Services.UserDetailsService;
// // import com.ProductManagement.product.Services.UserRoleMasterServices;
// // import com.ProductManagement.product.Repository.PrivilegeRepository;

// // @CrossOrigin(origins="http://localhost:3000")
// // @RestController

// // @RequestMapping("/users")
// // public class UserDetailsController {

// //     @Autowired
// //     private UserDetailsService usersService;

// //     @Autowired
// //     private PrivilegeRepository privilegeRepository;
    
// //     @Autowired
// //     private UserRoleMasterServices userRoleMasterService;

// //     @GetMapping
// //     public List<UserDetails> getAllUsers() {
// //         return usersService.getAllUsers();
// //     }

// //     @PostMapping
// //     public UserDetails createUser(@RequestBody UserDetails user) {
// //         // Logging the incoming request
// //         System.out.println("Received user creation request: " + user);
    
// //         // Check for null or empty body
// //         if (user == null || user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
// //             throw new EmptyRequestBodyException("Request body is empty or missing required fields.");
// //         }
    
// //         // Validate email format
// //         if (!isValidEmail(user.getEmail())) {
// //             throw new BadRequestException("Invalid email format: " + user.getEmail());
// //         }
    
// //         // Validate userRoleMaster
// //         if (user.getUserRoleMaster() == null || user.getUserRoleMaster().getUserRoleId() == null) {
// //             throw new BadRequestException("User role ID is required.");
// //         }
    
// //         // Fetch the role from the database
// //         UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
// //         if (userRole == null) {
// //             throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
// //         }
    
// //         // Assign the role to the user
// //         user.setUserRoleMaster(userRole);
    
// //         // Save the user
// //         return usersService.createUser(user);
// //     }
    
    

// //     @PutMapping("/{id}")
// //     public UserDetails updateUser(@PathVariable Long id, @RequestBody UserDetails user) {
// //         // Check if the request body is empty
// //         if (user == null || (user.getUserName() == null && user.getEmail() == null && user.getPassword() == null)) {
// //             throw new EmptyRequestBodyException("Request body is empty. Please provide user details.");
// //         }

// //         // Validate email format if email is provided
// //         if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
// //             throw new BadRequestException("Invalid email format: " + user.getEmail());
// //         }

// //         // Validate the user exists
// //         UserDetails existingUser = usersService.getUserById(id);
// //         if (existingUser == null) {
// //             throw new ResourceNotFoundException("User not found with ID: " + id);
// //         }

// //         // Validate the role ID and set the role if valid
// //         if (user.getUserRoleMaster() != null && user.getUserRoleMaster().getUserRoleId() != null) {
// //             UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
// //             if (userRole != null) {
// //                 user.setUserRoleMaster(userRole);  // Update the role in the user object
// //             } else {
// //                 throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
// //             }
// //         }

// //         // Update privileges for the user
// //         if (user.getPrivileges() != null && !user.getPrivileges().isEmpty()) {
// //             List<Privilege> privileges = privilegeRepository.findAllById(user.getPrivileges().stream()
// //                     .map(Privilege::getId)
// //                     .collect(Collectors.toList()));
// //             user.setPrivileges(privileges);
// //         }

// //         // Merge privileges from the role with the manual ones
// //         UserRoleMaster userRole = user.getUserRoleMaster();
// //         Set<Privilege> privilegesSet = new HashSet<>(userRole.getPrivileges());
// //         if (user.getPrivileges() != null) {
// //             privilegesSet.addAll(user.getPrivileges());
// //         }
// //         user.setPrivileges(new ArrayList<>(privilegesSet));

// //         user.setUserId(id);
// //         return usersService.updateUser(id, user);  
// //     }



    
// //     @GetMapping("/{id}")
// //     public UserDetails getUserById(@PathVariable Long id) {
// //         try {
// //             return usersService.getUserById(id);
// //         } catch (ResourceNotFoundException e) {
// //             throw new ResourceNotFoundException("User not found with ID: " + id);
// //         } catch (Exception e) {
// //             throw new RuntimeException("An unexpected error occurred while fetching user with ID: " + id, e);
// //         }
// //     }


// //     @DeleteMapping("/{id}")
// //     public void deleteUser(@PathVariable Long id) {
// //         usersService.deleteUser(id);
// //     }
    
// //     // Utility method for email validation
// //     private boolean isValidEmail(String email) {
// //         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
// //         return Pattern.compile(emailRegex).matcher(email).matches();
// //     }
// // }

// package com.ProductManagement.product.Controller;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import java.util.regex.Pattern;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.ProductManagement.product.Entity.Privilege;
// import com.ProductManagement.product.Entity.UserDetails;
// import com.ProductManagement.product.Entity.UserRoleMaster;
// import com.ProductManagement.product.ErrorHandle.BadRequestException;
// import com.ProductManagement.product.ErrorHandle.EmptyRequestBodyException;
// import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
// import com.ProductManagement.product.Services.JwtService;
// import com.ProductManagement.product.Services.UserDetailsService;
// import com.ProductManagement.product.Services.UserRoleMasterServices;
// import com.ProductManagement.product.Repository.PrivilegeRepository;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/users")
// public class UserDetailsController {

//     @Autowired
//     private UserDetailsService usersService;

//     @Autowired
//     private PrivilegeRepository privilegeRepository;

//     @Autowired
//     private UserRoleMasterServices userRoleMasterService;

//     @Autowired
//     private JwtService jwtService;

//     // Validate the JWT token
//     private void validateToken(String authorizationHeader) {
//         if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//             throw new BadRequestException("Authorization header is missing or invalid.");
//         }

//         String token = authorizationHeader.replace("Bearer ", "");

//         if (!jwtService.isValidToken(token)) {
//             throw new BadRequestException("Invalid or expired token.");
//         }
//     } public ResponseEntity<String> getHeaderValue(
//             @RequestHeader("Authorization") String authorizationHeader) {
//         // Print the value of the Authorization header
//         System.out.println("Authorization Header: " + authorizationHeader);

//         // Return a response with the header value
//         return ResponseEntity.ok("Received Authorization Header: " + authorizationHeader);
//     }

//     @GetMapping
//     public List<UserDetails> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         return usersService.getAllUsers();
//     }

//     @PostMapping
//     public UserDetails createUser(@RequestBody UserDetails user, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);

//         if (user == null || user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
//             throw new EmptyRequestBodyException("Request body is empty or missing required fields.");
//         }
//         if (!isValidEmail(user.getEmail())) {
//             throw new BadRequestException("Invalid email format: " + user.getEmail());
//         }
//         if (user.getUserRoleMaster() == null || user.getUserRoleMaster().getUserRoleId() == null) {
//             throw new BadRequestException("User role ID is required.");
//         }

//         UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
//         if (userRole == null) {
//             throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
//         }
//         user.setUserRoleMaster(userRole);
//         return usersService.createUser(user);
//     }

//     @PutMapping("/{id}")
//     public UserDetails updateUser(@PathVariable Long id, @RequestBody UserDetails user, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);

//         if (user == null || (user.getUserName() == null && user.getEmail() == null && user.getPassword() == null)) {
//             throw new EmptyRequestBodyException("Request body is empty. Please provide user details.");
//         }
//         if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
//             throw new BadRequestException("Invalid email format: " + user.getEmail());
//         }
//         UserDetails existingUser = usersService.getUserById(id);
//         if (existingUser == null) {
//             throw new ResourceNotFoundException("User not found with ID: " + id);
//         }
//         if (user.getUserRoleMaster() != null && user.getUserRoleMaster().getUserRoleId() != null) {
//             UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
//             if (userRole != null) {
//                 user.setUserRoleMaster(userRole);
//             } else {
//                 throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
//             }
//         }
//         if (user.getPrivileges() != null && !user.getPrivileges().isEmpty()) {
//             List<Privilege> privileges = privilegeRepository.findAllById(user.getPrivileges().stream()
//                     .map(Privilege::getId)
//                     .collect(Collectors.toList()));
//             user.setPrivileges(privileges);
//         }
//         UserRoleMaster userRole = user.getUserRoleMaster();
//         Set<Privilege> privilegesSet = new HashSet<>(userRole.getPrivileges());
//         if (user.getPrivileges() != null) {
//             privilegesSet.addAll(user.getPrivileges());
//         }
//         user.setPrivileges(new ArrayList<>(privilegesSet));
//         user.setUserId(id);
//         return usersService.updateUser(id, user);
//     }

//     @GetMapping("/{id}")
//     public UserDetails getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         try {
//             return usersService.getUserById(id);
//         } catch (ResourceNotFoundException e) {
//             throw new ResourceNotFoundException("User not found with ID: " + id);
//         } catch (Exception e) {
//             throw new RuntimeException("An unexpected error occurred while fetching user with ID: " + id, e);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//         validateToken(authorizationHeader);
//         usersService.deleteUser(id);
//     }

//     private boolean isValidEmail(String email) {
//         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//         return Pattern.compile(emailRegex).matcher(email).matches();
//     }
// }



package com.ProductManagement.product.Controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Entity.UserRoleMaster;
import com.ProductManagement.product.ErrorHandle.BadRequestException;
import com.ProductManagement.product.ErrorHandle.EmptyRequestBodyException;
import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
import com.ProductManagement.product.Services.JwtService;
import com.ProductManagement.product.Services.UserDetailsService;
import com.ProductManagement.product.Services.UserRoleMasterServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserDetailsController {

    @Autowired
    private UserDetailsService usersService;

    @Autowired
    private UserRoleMasterServices userRoleMasterService;

    @Autowired
    private JwtService jwtService;

    // Validate the JWT token
    private void validateToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization header is missing or invalid.");
        }

        String token = authorizationHeader.replace("Bearer ", "");

        if (!jwtService.isValidToken(token)) {
            throw new BadRequestException("Invalid or expired token.");
        }
    } public ResponseEntity<String> getHeaderValue(
            @RequestHeader("Authorization") String authorizationHeader) {
        // Print the value of the Authorization header
        System.out.println("Authorization Header: " + authorizationHeader);

        // Return a response with the header value
        return ResponseEntity.ok("Received Authorization Header: " + authorizationHeader);
    }

    @GetMapping
    public List<UserDetails> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        return usersService.getAllUsers();
    }

    @PostMapping
        public UserDetails createUser(@RequestBody UserDetails user, @RequestHeader("Authorization") String authorizationHeader) {
            validateToken(authorizationHeader);
    
            if (user == null || user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
                throw new EmptyRequestBodyException("Request body is empty or missing required fields.");
            }
            if (!isValidEmail(user.getEmail())) {
                throw new BadRequestException("Invalid email format: " + user.getEmail());
            }
            if (user.getUserRoleMaster() == null || user.getUserRoleMaster().getUserRoleId() == null) {
                throw new BadRequestException("User role ID is required.");
            }
    
            UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
            if (userRole == null) {
                throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
            }
            user.setUserRoleMaster(userRole);
            return usersService.createUser(user);
        }
    

    @PutMapping("/{id}")
    public UserDetails updateUser(@PathVariable Long id, @RequestBody UserDetails user,
            @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);

        if (user == null || (user.getUserName() == null && user.getEmail() == null && user.getPassword() == null)) {
            throw new EmptyRequestBodyException("Request body is empty. Please provide user details.");
        }

        if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
            throw new BadRequestException("Invalid email format: " + user.getEmail());
        }

        UserDetails existingUser = usersService.getUserById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        if (user.getUserRoleMaster() != null && user.getUserRoleMaster().getUserRoleId() != null) {
            UserRoleMaster userRole = userRoleMasterService.getRoleById(user.getUserRoleMaster().getUserRoleId());
            if (userRole != null) {
                user.setUserRoleMaster(userRole);
            } else {
                throw new ResourceNotFoundException("Role not found with ID: " + user.getUserRoleMaster().getUserRoleId());
            }
        }

        user.setUserId(id);
        return usersService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public UserDetails getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);

        try {
            return usersService.getUserById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching user with ID: " + id, e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validateToken(authorizationHeader);
        usersService.deleteUser(id);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}