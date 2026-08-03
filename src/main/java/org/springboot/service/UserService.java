package org.springboot.service;

import org.springboot.dto.LoginRequest;
import org.springboot.dto.LoginResponse;
import org.springboot.dto.UserDto;

public interface UserService {
	
	public Boolean register(UserDto userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
