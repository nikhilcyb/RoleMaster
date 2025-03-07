package com.ProductManagement.product.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.OrganisationMaster;


@Repository
public interface OrganizationRepository extends JpaRepository<OrganisationMaster, Long>{
    Optional<OrganisationMaster> findByEmail(String email);
    boolean existsByEmail(String email);

    List<OrganisationMaster> findByOrganizationNameContainingIgnoreCase(String name);
}
