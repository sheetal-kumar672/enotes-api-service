package org.springboot.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

public class BaseModel {
	
private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	


public void setDeleted(Boolean deleted)
{
	this.isDeleted = deleted;
}
public void setCreatedBy(Integer createdBy)
{
	this.createdBy = createdBy;
}
public void setCreatedOn(Date createdOn)
{
	this.createdOn = createdOn;
}
public void setIsActive(Boolean active)
{
	this.isActive = active;
}
}