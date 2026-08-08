package org.springboot.service;

import java.util.List;

import org.springboot.dto.TodoDto;

public interface TodoService {
	
	public Boolean saveTodo(TodoDto todo) throws Exception;
	
	public List<TodoDto> getTodoByUser();

	public TodoDto getTodoById(Integer id) throws Exception;

}
