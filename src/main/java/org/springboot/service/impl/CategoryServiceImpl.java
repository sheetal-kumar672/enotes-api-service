package org.springboot.service.impl;

import java.util.Date;
import java.util.List;

import org.springboot.entity.Category;
import org.springboot.repository.CategoryRepository;
import org.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(Category category) {
		category.setDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory))
		{
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		
		List<Category> categories = categoryRepo.findAll();
		return categories;
	}

}
