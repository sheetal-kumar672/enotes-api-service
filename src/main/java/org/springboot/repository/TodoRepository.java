package org.springboot.repository;

import java.util.List;

import org.springboot.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

	List<Todo> findByCreatedBy(Integer userId);

}
