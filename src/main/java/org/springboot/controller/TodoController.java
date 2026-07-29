package org.springboot.controller;

import java.util.List;

import org.springboot.dto.TodoDto;
import org.springboot.service.TodoService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception
	{
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo)
		{
			return CommonUtil.createBuildResponseMessage("Todo Saved Success", HttpStatus.CREATED);
		}
		else
		{
		return CommonUtil.createErrorResponseMessage("Todo Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> saveTodo(@PathVariable Integer id) throws Exception
	{
		TodoDto todo = todoService.getTodoById(id);

		return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getAllTodoByUser() 
	{
		List<TodoDto> todoList = todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(todoList))
		{
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
	}
}
