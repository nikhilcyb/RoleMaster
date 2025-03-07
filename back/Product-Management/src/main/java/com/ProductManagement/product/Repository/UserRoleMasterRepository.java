package com.ProductManagement.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductManagement.product.Entity.UserRoleMaster;
@Repository
public interface  UserRoleMasterRepository extends JpaRepository<UserRoleMaster,Long> {

}
