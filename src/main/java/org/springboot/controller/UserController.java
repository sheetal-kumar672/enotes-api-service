package org.springboot.controller;

import org.modelmapper.ModelMapper;
import org.springboot.dto.PasswordChangeRequest;
import org.springboot.dto.UserResponse;
import org.springboot.endpoint.UserEndpoint;
import org.springboot.entity.User;
import org.springboot.service.UserService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController implements UserEndpoint {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> getProfile()
	{
		User LoggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse =  mapper.map(LoggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> chnagePassword(PasswordChangeRequest passwordRequest)
	{
		userService.chnagePassword(passwordRequest);
		return CommonUtil.createBuildResponseMessage("Password change success", HttpStatus.OK);
	}
	
	
}
