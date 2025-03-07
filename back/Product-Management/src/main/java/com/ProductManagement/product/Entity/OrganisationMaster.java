package com.ProductManagement.product.Entity;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "organization_master")
public class OrganisationMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Org_ID", nullable = false, unique = true)
    private Long orgId; // Primary Key

    @Column(name = "organization_name",nullable = false)
    private String organizationName;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "org_description")
    private String orgDescription;

    @Column(name = "org_limit")
    private Integer orgLimit;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "addby_userid")
    private Integer addbyUserId;

    @Column(name = "editby_userid")
    private Integer editbyUserId;

    // Default constructor
    public OrganisationMaster() {}

    // Getters and setters...

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public Integer getOrgLimit() {
        return orgLimit;
    }

    public void setOrgLimit(Integer orgLimit) {
        this.orgLimit = orgLimit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getAddbyUserId() {
        return addbyUserId;
    }

    public void setAddbyUserId(Integer addbyUserId) {
        this.addbyUserId = addbyUserId;
    }

    public Integer getEditbyUserId() {
        return editbyUserId;
    }

    public void setEditbyUserId(Integer editbyUserId) {
        this.editbyUserId = editbyUserId;
    }
}
