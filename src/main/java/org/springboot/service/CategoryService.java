package org.springboot.service;

import java.util.List;


import org.springboot.dto.CategoryDto;
import org.springboot.dto.CategoryResponse;
import org.springboot.entity.Category;

public interface CategoryService {

	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDto getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategory(Integer id);
}
