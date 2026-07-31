package org.springboot.service;

import org.springboot.dto.UserDto;

public interface UserService {
	
	public Boolean register(UserDto userDto) throws Exception;

}
