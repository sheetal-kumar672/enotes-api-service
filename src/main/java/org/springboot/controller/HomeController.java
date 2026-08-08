package org.springboot.controller;

import org.springboot.dto.PswdResetRequest;
import org.springboot.service.HomeService;
import org.springboot.service.UserService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception
	{
		Boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount)
			return CommonUtil.createBuildResponseMessage("Account verification success",HttpStatus.OK);
		return CommonUtil.createErrorResponseMessage("Invalid Verification link", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest request) throws Exception
	{
		userService.sendEmailPasswordReset(email,request);
		return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
	}
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception
	{
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
	}
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception
	{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}

}
