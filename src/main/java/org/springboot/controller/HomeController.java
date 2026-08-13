package org.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.dto.PswdResetRequest;
import org.springboot.endpoint.HomeEndpoint;
import org.springboot.service.HomeService;
import org.springboot.service.UserService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class HomeController implements HomeEndpoint {
	
	Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> verifyUserAccount(Integer uid,String code) throws Exception
	{
		log.info("HomeController : verifyUserAccount() : Execution Start");
		Boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount)
			return CommonUtil.createBuildResponseMessage("Account verification success",HttpStatus.OK);
		
		log.info("HomeController : verifyUserAccount() : Execution End");
		return CommonUtil.createErrorResponseMessage("Invalid Verification link", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<?> sendEmailForPasswordReset(String email,HttpServletRequest request) throws Exception
	{
		userService.sendEmailPasswordReset(email,request);
		return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink(Integer uid,String code) throws Exception
	{
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> resetPassword(PswdResetRequest pswdResetRequest) throws Exception
	{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}

}
