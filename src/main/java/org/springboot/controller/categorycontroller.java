package org.springboot.controller;

import java.util.List;

import org.springboot.dto.CategoryDto;
import org.springboot.dto.CategoryResponse;
import org.springboot.entity.Category;
import org.springboot.service.CategoryService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class categorycontroller {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
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
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@GetMapping("/active")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
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
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id ) throws Exception
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

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deletecategoryById(@PathVariable Integer id )
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
