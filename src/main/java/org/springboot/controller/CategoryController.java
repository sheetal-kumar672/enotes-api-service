package org.springboot.controller;

import java.util.List;

import org.springboot.dto.CategoryDto;
import org.springboot.dto.CategoryResponse;
import org.springboot.endpoint.CategoryEndpoint;
import org.springboot.service.CategoryService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryEndpoint {
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> saveCategory(CategoryDto categoryDto)
	{
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("saved success", HttpStatus.CREATED);
			
//			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
			
		}
		else
		{
		    return CommonUtil.createErrorResponseMessage("category not saved ", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("not saved ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Override
	public ResponseEntity<?> getAllCategory()
	{
		
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
	     {
			return ResponseEntity.noContent().build();
			
		}
		else
		{
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
		
	}
	
	@Override
	public ResponseEntity<?> getActiveCategory()
	{
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
	     {
			return ResponseEntity.noContent().build();
			
		}
		else
		{
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
		
	}
	
	@Override
	public ResponseEntity<?> getCategoryDetailsById(Integer id ) throws Exception
	{
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryDto))
		{
			return CommonUtil.createErrorResponseMessage("Internal Server error", HttpStatus.NOT_FOUND);
//			return new ResponseEntity<>("Internal Server error", HttpStatus.NOT_FOUND);
		}
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
//		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deletecategoryById(Integer id )
	{
		Boolean deleted= categoryService.deleteCategory(id);
		if(deleted)
		{
			return CommonUtil.createBuildResponse("Category deleted success", HttpStatus.OK);
//			return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<>("category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
