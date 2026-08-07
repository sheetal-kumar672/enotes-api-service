package org.springboot.service;

import org.springboot.dto.PasswordChangeRequest;

public interface UserService {
	
	public void chnagePassword(PasswordChangeRequest passwordRequest);

}
