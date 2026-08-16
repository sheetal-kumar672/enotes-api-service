package org.springboot.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springboot.dto.CategoryDto;
import org.springboot.entity.Category;
import org.springboot.exception.ExistDataException;
import org.springboot.repository.CategoryRepository;
import org.springboot.service.impl.CategoryServiceImpl;
import org.springboot.util.Validation;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
	
	@Mock
	private CategoryRepository categoryRepo;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	@Mock
	private Validation validation;
	
	private CategoryDto categoryDto = null;
	private Category category = null;
	private List<Category> categories = new ArrayList<>();
	private List<CategoryDto> categoriesDto = new ArrayList<>();
	
	@Mock
	private ModelMapper mapper;
	
	@BeforeEach
	public void initalize()
	{
		 categoryDto = CategoryDto.builder()
				 .id(null)
				.name("Java Notes")
				.description("java notes")
				.isActive(true).build();
		 
		 category = Category.builder()
				 .id(null)
				.name("Java Notes")
				.description("java notes")
				.isDeleted(false)
				.isActive(true).build();
		 
		 categories.add(category);
		 categoriesDto.add(categoryDto);
		 
//		 category=mapper.map(categoryDto, Category.class);
	}
	
	@Test
	public void testSavecategory()
	{
		//arrange
	    when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
	    when(mapper.map(categoryDto, Category.class)).thenReturn(category);
	    when(categoryRepo.save(category)).thenReturn(category);
	    
	    // act
	    Boolean saveCategory = categoryService.saveCategory(categoryDto);
	    
	    // assert
	    assertTrue(saveCategory);
	    
	    // verify
	    verify(validation).categoryValidation(categoryDto);
	    verify(categoryRepo).existsByName(categoryDto.getName());
	    verify(categoryRepo).save(category);   
	    
	}
	
	@Test
	public void testCategoryExist()
	{
	    when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(true);
        Exception exception = assertThrows(ExistDataException.class, ()->{
        	categoryService.saveCategory(categoryDto); 	
        });
        
        assertEquals("Category already exist", exception.getMessage());
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepo).existsByName(categoryDto.getName());
        verify(categoryRepo,never()).save(category);
	}
	
	@Test
	public void testUpdatecategory()
	{
		categoryDto.setId(1);
		category.setId(1);
		
		//arrange
	    when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
	    when(mapper.map(categoryDto, Category.class)).thenReturn(category);
	    when(categoryRepo.save(category)).thenReturn(category);
	    
	    // act
	    Boolean saveCategory = categoryService.saveCategory(categoryDto);
	    
	    // assert
	    assertTrue(saveCategory);
	    
	    // verify
	    verify(validation).categoryValidation(categoryDto);
	    verify(categoryRepo).existsByName(categoryDto.getName());
	    verify(categoryRepo).save(category);   
	    
	}
	
	@Test
	public void testGetAllCategory()
	{
		when(categoryRepo.findByIsDeletedFalse()).thenReturn(categories);
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		assertEquals(allCategory.size(), categories.size());
		verify(categoryRepo).findByIsDeletedFalse();
	}

}
