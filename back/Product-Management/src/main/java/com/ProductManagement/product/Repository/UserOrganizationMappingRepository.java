package com.ProductManagement.product.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.OrganisationMaster;
import com.ProductManagement.product.Entity.UserDetails;
import com.ProductManagement.product.Entity.UserOrganizationMapping;

@Repository
public interface UserOrganizationMappingRepository extends CrudRepository<UserOrganizationMapping, Long> {

    // Check if the user is already connected to the organization using UserDetails and OrganisationMaster
    boolean existsByUserDetailsAndOrganisationMaster(UserDetails userDetails, OrganisationMaster organisationMaster);

    // Add other necessary methods if needed
}
