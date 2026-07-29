package org.springboot.util;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springboot.dto.CategoryDto;
import org.springboot.dto.TodoDto;
import org.springboot.dto.TodoDto.StatusDto;
import org.springboot.enums.TodoStatus;
import org.springboot.exception.ResourceNotFoundException;
import org.springboot.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class Validation {
	
	public void categoryValidation(CategoryDto categoryDto)
	{
		
		Map<String, Object> error = new LinkedHashMap<>();
		
		if (ObjectUtils.isEmpty(categoryDto))
		{
			throw new IllegalArgumentException("category Object/JSON shouldn't be null or empty");
		}
		else
		{
			//Validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			}
			else
			{
				if(categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if(categoryDto.getName().length() > 100) {
					error.put("name", "name length max 10");
				}
			}
			
			// Validation description
			
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is empty or null");
			}
			
			// Validation isActive
			
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("IsActive", "IsActive field is empty or null");
			}
			else
			{
				if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue() ) {
					error.put("isActive", "invalid value isActive field");
				}
				
			}
		}
		
		if(!error.isEmpty())
		{
			throw new ValidationException(error);
		}
		
	}
	
	public void todoValidation(TodoDto todo) throws Exception 
	{
		StatusDto reqstatus = todo.getStatus();
		
		Boolean statusFound = false;
		
		for(TodoStatus st : TodoStatus.values())
		{
			if(st.getId().equals(reqstatus.getId()))
			{
				statusFound = true;
			}
		}
		if(!statusFound)
		{
			throw new ResourceNotFoundException("Invalid status");
		}
	}
	
	

}
