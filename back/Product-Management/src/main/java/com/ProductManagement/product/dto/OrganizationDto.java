package com.ProductManagement.product.dto;



import java.time.LocalDateTime;

public class OrganizationDto {
	
	 	public Long orgId;
	    public  String organizationName;
	    public String email;
	    public String contactNo;
	    public String domainName;
	    public  Boolean isEnabled;
	    public String orgDescription;
	    public Integer limit;
	    public  LocalDateTime createdAt;
	    public LocalDateTime updatedAt;
	    public Integer addUserId;
	    public Integer editUserId;
	    
	    
	    
	    
		public OrganizationDto(Long orgId, String organizationName, String email, String contactNo, String domainName,
				Boolean isEnabled, String orgDescription, Integer limit, LocalDateTime createdAt,
				LocalDateTime updatedAt, Integer addUserId, Integer editUserId) {
			super();
			this.orgId = orgId;
			this.organizationName = organizationName;
			this.email = email;
			this.contactNo = contactNo;
			this.domainName = domainName;
			this.isEnabled = isEnabled;
			this.orgDescription = orgDescription;
			this.limit = limit;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.addUserId = addUserId;
			this.editUserId = editUserId;
		}
	    
	    
	    
	    
	    
	    
}
