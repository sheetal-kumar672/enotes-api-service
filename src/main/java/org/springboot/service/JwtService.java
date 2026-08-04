package org.springboot.service;

import org.springboot.entity.User;

public interface JwtService {
	
	public String generateToken(User user);

	

}
