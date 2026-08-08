package org.springboot.service;

import org.springboot.dto.LoginRequest;
import org.springboot.dto.LoginResponse;
import org.springboot.dto.UserRequest;

public interface AuthService {
	
	public Boolean register(UserRequest userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
