package com.ProductManagement.product.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.Privilege;
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

}
