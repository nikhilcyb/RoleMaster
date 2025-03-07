// package com.ProductManagement.product.Services;



// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// // import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.stereotype.Service;

// import com.ProductManagement.product.Entity.Privilege;
// import com.ProductManagement.product.Entity.UserRoleMaster;
// import com.ProductManagement.product.Repository.PrivilegeRepository;
// import com.ProductManagement.product.Repository.UserRoleMasterRepository;

// @Service
// public class UserRoleMasterServices {

//     @Autowired
//     private UserRoleMasterRepository userRoleMasterRepository;

//     @Autowired
//     private PrivilegeRepository privilegeRepository;

//     // // Get all roles
//     // public List<UserRoleMaster> getAllRoles() {
//     //     return userRoleRepository.findAll();
//     // }

//     // // Create a new role with privileges
//     // public UserRoleMaster createRole(UserRoleMaster role) {
//     //     Set<Privilege> privileges = role.getPrivileges();
//     //     if (privileges != null && !privileges.isEmpty()) {
//     //         // Fetch privileges from the database to ensure they exist
//     //         privileges = privilegeRepository.findAllById(
//     //             privileges.stream().map(Privilege::getId).toList()
//     //         ).stream().collect(Collectors.toSet());
//     //         role.setPrivileges(privileges);
//     //     }
//     //     return userRoleRepository.save(role);
//     // }

//     // // Get a role by ID
//     // public UserRoleMaster getRoleById(Long id) {
//     //     return userRoleRepository.findById(id)
//     //             .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
//     // }

//     // // Delete a role
//     // public void deleteRole(Long id) {
//     //     userRoleRepository.deleteById(id);
//     // }
//     // Method to get all roles
//     public List<UserRoleMaster> getAllRoles() {
//         List<UserRoleMaster> roles = userRoleMasterRepository.findAll();
//         if (roles.isEmpty()) {
//             throw new RuntimeException("No roles found.");
//         }
//         return roles;
//     }

//     // Method to create a new role
//     public UserRoleMaster createRole(UserRoleMaster role) {
//         if (role == null || role.getRoleName() == null || role.getRoleName().trim().isEmpty()) {
//             throw new IllegalArgumentException("Role name must not be null or empty.");
//         }
        
//         if (role.getPrivileges() != null) {
//             Set<Privilege> existingPrivileges = new HashSet<>();
            
//             // Check if the privileges already exist, if yes, use the existing ones.
//             for (Privilege privilege : role.getPrivileges()) {
//                 if (privilege.getId() == null || privilege.getId() <= 0) {
//                     throw new IllegalArgumentException("Invalid privilege ID: " + privilege.getId());
//                 }
//                 Privilege existingPrivilege = privilegeRepository.findById(privilege.getId()).orElse(null);
//                 if (existingPrivilege != null) {
//                     existingPrivileges.add(existingPrivilege);
//                 } else {
//                     existingPrivileges.add(privilege); // New privilege to be saved
//                 }
//             }
            
//             // Set the role's privileges to the existing or new ones
//             role.setPrivileges(existingPrivileges);
//         }
//         return userRoleMasterRepository.save(role);
//     }

//     // Method to update an existing role
//     public UserRoleMaster updateRole(Long id, UserRoleMaster updatedRole) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Role ID must be a positive number.");
//         }
        
//         UserRoleMaster existingRole = userRoleMasterRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        
//         if (updatedRole.getRoleName() != null && !updatedRole.getRoleName().trim().isEmpty()) {
//             existingRole.setRoleName(updatedRole.getRoleName());
//         }
        
//         if (updatedRole.getPrivileges() != null) {
//             Set<Privilege> updatedPrivileges = new HashSet<>();
//             for (Privilege privilege : updatedRole.getPrivileges()) {
//                 if (privilege.getId() == null || privilege.getId() <= 0) {
//                     throw new IllegalArgumentException("Invalid privilege ID: " + privilege.getId());
//                 }
//                 Privilege existingPrivilege = privilegeRepository.findById(privilege.getId()).orElse(null);
//                 if (existingPrivilege != null) {
//                     updatedPrivileges.add(existingPrivilege);
//                 } else {
//                     updatedPrivileges.add(privilege);
//                 }
//             }
//             existingRole.setPrivileges(updatedPrivileges);
//         }
        
//         return userRoleMasterRepository.save(existingRole);
//     }

//     // Method to get a role by ID
//     public UserRoleMaster getRoleById(Long id) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Role ID must be a positive number.");
//         }
//         return userRoleMasterRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
//     }

//     // Method to delete a role
//     public void deleteRole(Long id) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Role ID must be a positive number.");
//         }
//         try {
//             userRoleMasterRepository.deleteById(id);
//         } catch (EmptyResultDataAccessException e) {
//             throw new RuntimeException("Cannot delete. Role not found with ID: " + id);
//         }
//     }

//     // Example of usage in deletePrivilege
//     public void deletePrivilege(Long id) {
//         if (id == null || id <= 0) {
//             throw new IllegalArgumentException("Privilege ID must be a positive number.");
//         }
//         try {
//             // Check if privilege is associated with any role
//             boolean isPrivilegeInUse = userRoleMasterRepository.existsById(id);
//             if (isPrivilegeInUse) {
//                 throw new RuntimeException("Cannot delete. Privilege is associated with roles.");
//             }

//             // Delete privilege if not in use
//             privilegeRepository.deleteById(id);
//         } catch (EmptyResultDataAccessException e) {
//             throw new RuntimeException("Cannot delete. Privilege not found with ID: " + id);
//         }
//     }

// }



package com.ProductManagement.product.Services;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.ProductManagement.product.Entity.Privilege;
import com.ProductManagement.product.Entity.UserRoleMaster;
import com.ProductManagement.product.Repository.PrivilegeRepository;
import com.ProductManagement.product.Repository.UserRoleMasterRepository;

@Service
public class UserRoleMasterServices {

    @Autowired
    private UserRoleMasterRepository userRoleMasterRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    // Method to get all roles
    public List<UserRoleMaster> getAllRoles() {
        try {
            List<UserRoleMaster> roles = userRoleMasterRepository.findAll();
            if (roles.isEmpty()) {
                throw new RuntimeException("No roles found.");
            }
            return roles;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching roles: " + e.getMessage(), e);
        }
    }

    // Method to create a new role
    public UserRoleMaster createRole(UserRoleMaster role) {
        try {
            if (role == null || role.getRoleName() == null || role.getRoleName().trim().isEmpty()) {
                throw new IllegalArgumentException("Role name must not be null or empty.");
            }
            
            if (role.getPrivileges() != null) {
                Set<Privilege> existingPrivileges = new HashSet<>();
                
                for (Privilege privilege : role.getPrivileges()) {
                    if (privilege.getId() == null || privilege.getId() <= 0) {
                        throw new IllegalArgumentException("Invalid privilege ID: " + privilege.getId());
                    }
                    Privilege existingPrivilege = privilegeRepository.findById(privilege.getId()).orElse(null);
                    if (existingPrivilege != null) {
                        existingPrivileges.add(existingPrivilege);
                    } else {
                        existingPrivileges.add(privilege);
                    }
                }
                role.setPrivileges(existingPrivileges);
            }
            return userRoleMasterRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException("Error creating role: " + e.getMessage(), e);
        }
    }

    // Method to update an existing role
    public UserRoleMaster updateRole(Long id, UserRoleMaster updatedRole) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Role ID must be a positive number.");
            }
            
            UserRoleMaster existingRole = userRoleMasterRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
            
            if (updatedRole.getRoleName() != null && !updatedRole.getRoleName().trim().isEmpty()) {
                existingRole.setRoleName(updatedRole.getRoleName());
            }
            
            if (updatedRole.getPrivileges() != null) {
                Set<Privilege> updatedPrivileges = new HashSet<>();
                for (Privilege privilege : updatedRole.getPrivileges()) {
                    if (privilege.getId() == null || privilege.getId() <= 0) {
                        throw new IllegalArgumentException("Invalid privilege ID: " + privilege.getId());
                    }
                    Privilege existingPrivilege = privilegeRepository.findById(privilege.getId()).orElse(null);
                    if (existingPrivilege != null) {
                        updatedPrivileges.add(existingPrivilege);
                    } else {
                        updatedPrivileges.add(privilege);
                    }
                }
                existingRole.setPrivileges(updatedPrivileges);
            }
            return userRoleMasterRepository.save(existingRole);
        } catch (Exception e) {
            throw new RuntimeException("Error updating role: " + e.getMessage(), e);
        }
    }

    // Method to get a role by ID
    public UserRoleMaster getRoleById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Role ID must be a positive number.");
            }
            return userRoleMasterRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching role: " + e.getMessage(), e);
        }
    }

    // Method to delete a role
    public void deleteRole(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Role ID must be a positive number.");
            }
            userRoleMasterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Cannot delete. Role not found with ID: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting role: " + e.getMessage(), e);
        }
    }

    // Method to delete a privilege
    public void deletePrivilege(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Privilege ID must be a positive number.");
            }
            
            boolean isPrivilegeInUse = userRoleMasterRepository.existsById(id);
            if (isPrivilegeInUse) {
                throw new RuntimeException("Cannot delete. Privilege is associated with roles.");
            }
            
            privilegeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Cannot delete. Privilege not found with ID: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting privilege: " + e.getMessage(), e);
        }
    }
}