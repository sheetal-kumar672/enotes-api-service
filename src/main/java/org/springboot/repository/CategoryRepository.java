package org.springboot.repository;

import java.util.List;

import org.springboot.dto.CategoryResponse;
import org.springboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findByIsActiveTrue();

}
