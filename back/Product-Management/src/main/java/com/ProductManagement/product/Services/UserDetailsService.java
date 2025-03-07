// package com.ProductManagement.product.Services;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import java.util.regex.Pattern;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.ProductManagement.product.Entity.Privilege;
// import com.ProductManagement.product.Entity.UserDetails;
// import com.ProductManagement.product.Entity.UserRoleMaster;
// import com.ProductManagement.product.ErrorHandle.BadRequestException;
// import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
// import com.ProductManagement.product.Repository.UserRoleMasterRepository;
// import com.ProductManagement.product.Repository.UsersRepository;

// import jakarta.transaction.Transactional;

// @Service
// public class UserDetailsService {

//     @Autowired
//     private UsersRepository usersRepository;
    
//     @Autowired
//     private UserRoleMasterRepository userRoleMasterRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder

//     public List<UserDetails> getAllUsers() {
//         return usersRepository.findAll();
//     }

//     public UserDetails createUser(UserDetails user) {
//         // Ensure email is validated before saving
//         if (!isValidEmail(user.getEmail())) {
//             throw new BadRequestException("Invalid email format: " + user.getEmail());
//         }

//         // Hash the password before saving the user
//         String hashedPassword = passwordEncoder.encode(user.getPassword());
//         user.setPassword(hashedPassword);  // Set the hashed password

//         // Fetch the role from the database
//         UserRoleMaster userRole = userRoleMasterRepository.findById(user.getUserRoleMaster().getUserRoleId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

//         // Merge the privileges: first assign the role privileges, then add manually assigned privileges
//         Set<Privilege> combinedPrivileges = new HashSet<>(userRole.getPrivileges());
        
//         if (user.getPrivileges() != null) {
//             // Add manually assigned privileges (removing duplicates)
//             combinedPrivileges.addAll(user.getPrivileges());
//         }

//         // Set combined privileges to the user
//         user.setPrivileges(new ArrayList<>(combinedPrivileges));

//         // Save the user
//         return usersRepository.save(user);
//     }

//     public UserDetails getUserById(Long id) {
//         return usersRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//     }

//     public void deleteUser(Long id) {
//         usersRepository.deleteById(id);
//     }

//     @Transactional
//     public UserDetails updateUser(Long id, UserDetails updatedUser) {
//         UserDetails existingUser = usersRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

//         if (updatedUser.getPassword() != null) {
//             // Encrypt the updated password if it's provided
//             String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
//             existingUser.setPassword(hashedPassword);
//         }

//         // Update other fields as necessary...
//         if (updatedUser.getUserName() != null) {
//             existingUser.setUserName(updatedUser.getUserName());
//         }
//         if (updatedUser.getEmail() != null) {
//             existingUser.setEmail(updatedUser.getEmail());
//         }
//         if (updatedUser.getApiKey() != null) {
//             existingUser.setApiKey(updatedUser.getApiKey());
//         }
//         if (updatedUser.getUserRoleMaster() != null) {
//             existingUser.setUserRoleMaster(updatedUser.getUserRoleMaster());

//             // Combine the privileges from the updated role and existing manually assigned privileges
//             UserRoleMaster userRole = userRoleMasterRepository.findById(updatedUser.getUserRoleMaster().getUserRoleId())
//                     .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

//             Set<Privilege> combinedPrivileges = new HashSet<>(userRole.getPrivileges());

//             if (updatedUser.getPrivileges() != null) {
//                 // Add manually assigned privileges (removing duplicates)
//                 combinedPrivileges.addAll(updatedUser.getPrivileges());
//             }

//             // Set the combined privileges to the user
//             existingUser.setPrivileges(new ArrayList<>(combinedPrivileges));
//         }

//         // Set the fields that were updated (including timestamp and updater info)
//         existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
//         existingUser.setUpdatedAt(LocalDateTime.now());

//         return usersRepository.save(existingUser);
//     }

//     // Utility method for email validation
//     private boolean isValidEmail(String email) {
//         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//         return Pattern.compile(emailRegex).matcher(email).matches();
//     }
// }

package com.ProductManagement.product.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Entity.UserRoleMaster;
import com.ProductManagement.product.Entity.Privilege;
import com.ProductManagement.product.ErrorHandle.BadRequestException;
import com.ProductManagement.product.ErrorHandle.ResourceNotFoundException;
import com.ProductManagement.product.Repository.UsersRepository;
import com.ProductManagement.product.Repository.UserRoleMasterRepository;
import com.ProductManagement.product.Repository.PrivilegeRepository;

@Service
public class UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMasterRepository userRoleMasterRepository; // Added this to handle the user roles

    @Autowired
    private PrivilegeRepository privilegeRepository; // Added to manage privileges

    public List<UserDetails> getAllUsers() {
        return usersRepository.findAll();
    }

    public UserDetails createUser(UserDetails user) {
        // Ensure email is validated before saving
        if (!isValidEmail(user.getEmail())) {
            throw new BadRequestException("Invalid email format: " + user.getEmail());
        }

        // Hash the password before saving the user
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);  // Set the hashed password

        // Fetch the role from the database
        UserRoleMaster userRole = userRoleMasterRepository.findById(user.getUserRoleMaster().getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        // Merge the privileges: first assign the role privileges, then add manually assigned privileges
        Set<Privilege> combinedPrivileges = new HashSet<>(userRole.getPrivileges());

        if (user.getPrivileges() != null) {
            // Add manually assigned privileges (removing duplicates)
            combinedPrivileges.addAll(user.getPrivileges());
        }

        // Set combined privileges to the user
        user.setPrivileges(new ArrayList<>(combinedPrivileges));

        // Save the user
        return usersRepository.save(user);
    }

    public UserDetails getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public UserDetails updateUser(Long id, UserDetails updatedUser) {
        UserDetails existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (updatedUser.getPassword() != null) {
            // Hash the updated password
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Update other fields
        if (updatedUser.getUserName() != null) {
            existingUser.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getApiKey() != null) {
            existingUser.setApiKey(updatedUser.getApiKey());
        }
        if (updatedUser.getUserRoleMaster() != null) {
            existingUser.setUserRoleMaster(updatedUser.getUserRoleMaster());
        }

        existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return usersRepository.save(existingUser);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
