package com.ProductManagement.product.Entity;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;

@Entity
public class UserRoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    private String roleName;
    private String roleDescription;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "role_privileges",
        joinColumns =@JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();

    // Getters and setters        
    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
    
    

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
    public String getroleDescription() {
        return roleDescription;
    }

    public void setroleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
    
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
    
    
}
