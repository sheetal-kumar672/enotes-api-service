package org.springboot.service;

import java.util.List;

import org.springboot.entity.Category;

public interface CategoryService {

	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();
}
