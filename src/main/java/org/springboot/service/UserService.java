package org.springboot.service;

import org.springboot.dto.PasswordChangeRequest;
import org.springboot.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	public void chnagePassword(PasswordChangeRequest passwordRequest);

	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

	public void verifyPswdResetLink(Integer uid, String code) throws Exception;

	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;

}
