package org.springboot.endpoint;

import org.springboot.dto.CategoryDto;
import org.springboot.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springboot.util.Constants.ROLE_ADMIN;
import static org.springboot.util.Constants.ROLE_ADMIN_USER;

@Tag(name = "Category",description  = "All the Category operation APIs")
@RequestMapping("/api/category")
public interface CategoryEndpoint {

	@Operation(summary = "Save Category",tags = {"Category"},description = "Admin Save Category")
	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	
	@Operation(summary = "Get All Category",tags = {"Category"},description = "Admin Get All Category")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN) 
	public ResponseEntity<?> getAllCategory();
	
	
	@Operation(summary = "Get Active category",tags = {"Category"},description = "Admin,User Get Active Category")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	
	@Operation(summary = "Get Category Details By Id",tags = {"Category"},description = "Admin Get Category Details By Id ")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id ) throws Exception;
	
	
	@Operation(summary = "Delete Category By Id",tags = {"Category"},description = "Admin Delete Category By Id ")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deletecategoryById(@PathVariable Integer id );
}
