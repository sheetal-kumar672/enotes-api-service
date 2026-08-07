package org.springboot.controller;

import org.springboot.dto.LoginRequest;
import org.springboot.dto.LoginResponse;
import org.springboot.dto.UserRequest;
import org.springboot.service.UserService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception
	{
		String url = CommonUtil.getUrl(request);
		Boolean register = userService.register(userDto,url);
		if(register)
		{
			return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception
	{
		LoginResponse loginResponse = userService.login(loginRequest);
		if(ObjectUtils.isEmpty(loginResponse))
		{
			return CommonUtil.createBuildResponseMessage("invalid credential", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}

}
