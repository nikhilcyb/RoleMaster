// package com.ProductManagement.product.Services;



// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.stereotype.Service;

// import com.ProductManagement.product.Entity.Privilege;
// import com.ProductManagement.product.Repository.PrivilegeRepository;

// @Service
// public class PrivilegeService {

// 	@Autowired
//     private PrivilegeRepository privilegeRepository;
	
// 	 // Method to get all privileges
//     // public List<Privilege> getAllPrivileges() {
//     //     return privilegeRepository.findAll();
//     // }

//     // // Method to create a new privilege
//     // public Privilege createPrivilege(Privilege privilege) {
//     //     return privilegeRepository.save(privilege);
//     // }

//     // // Method to get a privilege by ID
//     // public Privilege getPrivilegeById(Long id) {
//     //     return privilegeRepository.findById(id)
//     //             .orElseThrow(() -> new RuntimeException("Privilege not found with ID: " + id));
//     // }

//     // // Method to delete a privilege
//     // public void deletePrivilege(Long id) {
//     //     privilegeRepository.deleteById(id);
//     // }

//     // Method to get all privileges
//     public List<Privilege> getAllPrivileges() {
//         List<Privilege> privileges = privilegeRepository.findAll();
//         if (privileges.isEmpty()) {
//             throw new RuntimeException("No privileges found.");
//         }
//         return privileges;
//     }

//     // Method to create a new privilege
//     public Privilege createPrivilege(Privilege privilege) {
//         if (privilege == null || privilege.getName() == null || privilege.getName().trim().isEmpty()) {
//             throw new IllegalArgumentException("Privilege name must not be null or empty.");
//         }
//         return privilegeRepository.save(privilege);
//     }

//     // Method to get a privilege by ID
//     public Privilege getPrivilegeById(Long id) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Privilege ID must be a positive number.");
//         }
//         return privilegeRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Privilege not found with ID: " + id));
//     }

//     // Method to delete a privilege
//     public void deletePrivilege(Long id) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Privilege ID must be a positive number.");
//         }
//         try {
//             privilegeRepository.deleteById(id);
//         } catch (EmptyResultDataAccessException e) {
//             throw new RuntimeException("Cannot delete. Privilege not found with ID: " + id);
//         }
//     }

//     // Method to update an existing privilege
//     public Privilege updatePrivilege(Long id, Privilege updatedPrivilege) {
//     Privilege existingPrivilege = privilegeRepository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Privilege not found with ID: " + id));
    
//     // Update the existing privilege with new values
//     existingPrivilege.setName(updatedPrivilege.getName());
//     // Add other fields as necessary
    
//     return privilegeRepository.save(existingPrivilege);
// }

// }



package com.ProductManagement.product.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ProductManagement.product.Entity.Privilege;
import com.ProductManagement.product.Repository.PrivilegeRepository;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    // Method to get all privileges
    public List<Privilege> getAllPrivileges() {
        try {
            List<Privilege> privileges = privilegeRepository.findAll();
            if (privileges.isEmpty()) {
                throw new RuntimeException("No privileges found.");
            }
            return privileges;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching privileges: " + e.getMessage(), e);
        }
    }

    // Method to create a new privilege
    public Privilege createPrivilege(Privilege privilege) {
        try {
            if (privilege == null || privilege.getName() == null || privilege.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Privilege name must not be null or empty.");
            }
            return privilegeRepository.save(privilege);
        } catch (Exception e) {
            throw new RuntimeException("Error creating privilege: " + e.getMessage(), e);
        }
    }

    // Method to get a privilege by ID
    public Privilege getPrivilegeById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Privilege ID must be a positive number.");
            }
            return privilegeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Privilege not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching privilege: " + e.getMessage(), e);
        }
    }

    // Method to delete a privilege
    public void deletePrivilege(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Privilege ID must be a positive number.");
            }
            privilegeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Cannot delete. Privilege not found with ID: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting privilege: " + e.getMessage(), e);
        }
    }

    // Method to update an existing privilege
    public Privilege updatePrivilege(Long id, Privilege updatedPrivilege) {
        try {
            Privilege existingPrivilege = privilegeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Privilege not found with ID: " + id));
    
            // Update the existing privilege with new values
            existingPrivilege.setName(updatedPrivilege.getName());
            // Add other fields as necessary
    
            return privilegeRepository.save(existingPrivilege);
        } catch (Exception e) {
            throw new RuntimeException("Error updating privilege: " + e.getMessage(), e);
        }
    }
}