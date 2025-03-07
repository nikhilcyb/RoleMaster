package com.ProductManagement.product.Services;



import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProductManagement.product.Entity.OrganisationMaster;
import com.ProductManagement.product.Repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrganisationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<OrganisationMaster> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public OrganisationMaster getOrganizationById(Long id) {
        return organizationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Organization not found with ID: " + id));
    }

    //create
    public OrganisationMaster createOrganization(OrganisationMaster organization) {
        if (!isValidEmail(organization.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (organizationRepository.existsByEmail(organization.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
            

        }

        return organizationRepository.save(organization);
    }

    
    //update
    public OrganisationMaster updateOrganization(Long id, OrganisationMaster organizationDetails) {
        OrganisationMaster organization = getOrganizationById(id);

    
   
        if(organizationDetails.getOrganizationName() != null ) {
        	
        	organization.setOrganizationName(organizationDetails.getOrganizationName());
  	
        }       	
        
        if(organizationDetails.getEmail() != null) {
            organization.setEmail(organizationDetails.getEmail());
            
            if (!isValidEmail(organizationDetails.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
            

        }
        
        if(organizationDetails.getContactNo() != null) {
            organization.setContactNo(organizationDetails.getContactNo());

        }
        
        if (organizationDetails.getDomainName() != null) {
            organization.setDomainName(organizationDetails.getDomainName());
        	
        }
        
        if(organizationDetails.getIsEnabled() != null) {
            organization.setIsEnabled(organizationDetails.getIsEnabled());

        }
        
        if(organizationDetails.getOrgDescription() != null) {
            organization.setOrgDescription(organizationDetails.getOrgDescription());

        }
        
        
        if(organizationDetails.getOrgLimit() != null) {
            organization.setOrgLimit(organizationDetails.getOrgLimit());
        }

        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        OrganisationMaster organization = getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    public List<OrganisationMaster> searchOrganizationsByName(String query) {
        // Assuming you have a JpaRepository for OrganisationMaster
        return organizationRepository.findByOrganizationNameContainingIgnoreCase(query);
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
