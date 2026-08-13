package org.springboot.endpoint;

import static org.springboot.util.Constants.ROLE_USER;

import org.springboot.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo",description  = "All the Todo Operation APIs")
@RequestMapping("/api/todo")
public interface TodoEndpoint {

	@Operation(summary = "Save Todo",tags = {"Todo"},description = "Save Todo")
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	
	@Operation(summary = "Get Todo By Id",tags = {"Todo"},description = "Get Todo")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get All Todo By User",tags = {"Todo"},description = "Get All Todo By User")
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser() ;
}
