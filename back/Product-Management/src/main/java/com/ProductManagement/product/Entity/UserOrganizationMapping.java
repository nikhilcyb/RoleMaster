package com.ProductManagement.product.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserOrganizationMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserDetails userDetails; // Foreign Key to UserDetails

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "Org_ID")
    private OrganisationMaster organisationMaster; // Foreign Key to OrganisationMaster

    // Default constructor
    public UserOrganizationMapping() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public OrganisationMaster getOrganisationMaster() {
        return organisationMaster;
    }

    public void setOrganisationMaster(OrganisationMaster organisationMaster) {
        this.organisationMaster = organisationMaster;
    }
}
