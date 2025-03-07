// package com.ProductManagement.product.Services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.ProductManagement.product.Repository.OrganizationRepository;
// import com.ProductManagement.product.Repository.UsersRepository;

// @Service
// public class AccessService {

//     @Autowired
//     private UsersRepository userRepository;

//     @Autowired
//     private OrganizationRepository organizationRepository;

//     public String checkAccess(Long userId, Long organizationId) {
//         boolean userExists = userRepository.existsById(userId);
//         boolean organizationExists = organizationRepository.existsById(organizationId);

//         if (!userExists && !organizationExists) {
//             return "Access Denied: Both User ID and Organization ID are invalid.";
//         } else if (!userExists) {
//             return "Access Denied: Invalid User ID.";
//         } else if (!organizationExists) {
//             return "Access Denied: Invalid Organization ID.";
//         }

//         return "Access Granted.";
//     }

//     public String checkIfAlreadyConnected(Long userId, Long organizationId) {
//         if (userId.equals(organizationId)) { 
//             return "User and Organization are already connected.";
//         }
//         return checkAccess(userId, organizationId);
//     }
// }



package com.ProductManagement.product.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProductManagement.product.Entity.OrganisationMaster;
import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Entity.UserOrganizationMapping;
import com.ProductManagement.product.Repository.OrganizationRepository;
import com.ProductManagement.product.Repository.UserOrganizationMappingRepository;
import com.ProductManagement.product.Repository.UsersRepository;  // Importing List class
@Service
public class AccessService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserOrganizationMappingRepository userOrganizationMappingRepository;  // Inject the repository

    public String checkAccess(Long userId, Long organizationId) {
        boolean userExists = userRepository.existsById(userId);
        boolean organizationExists = organizationRepository.existsById(organizationId);

        if (!userExists && !organizationExists) {
            return "Access Denied: Both User ID and Organization ID are invalid.";
        } else if (!userExists) {
            return "Access Denied: Invalid User ID.";
        } else if (!organizationExists) {
            return "Access Denied: Invalid Organization ID.";
        }

        return "Access Granted.";
    }

    public String checkIfAlreadyConnected(Long userId, Long organizationId) {
        // Fetch the UserDetails and OrganisationMaster objects
        UserDetails userDetails = userRepository.findById(userId).orElse(null);
        OrganisationMaster organisationMaster = organizationRepository.findById(organizationId).orElse(null);

        if (userDetails == null || organisationMaster == null) {
            return "Invalid User or Organization ID.";
        }

        // Check if the user and organization are already connected in the mapping table
        boolean isConnected = userOrganizationMappingRepository.existsByUserDetailsAndOrganisationMaster(userDetails, organisationMaster);

        if (isConnected) {
            return "User and Organization are already connected.";
        } else {
            // Save the connection if not connected
            UserOrganizationMapping mapping = new UserOrganizationMapping();
            mapping.setUserDetails(userDetails); // Set the user details
            mapping.setOrganisationMaster(organisationMaster); // Set the organization details
            userOrganizationMappingRepository.save(mapping); // Save mapping in the database
            
            return "User and Organization are not connected. Now connected!";
        }
    }


    public List<UserOrganizationMapping> getAllMappings() {
        return (List<UserOrganizationMapping>) userOrganizationMappingRepository.findAll();
    }
}
