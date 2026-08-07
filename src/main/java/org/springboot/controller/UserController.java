package org.springboot.controller;

import org.modelmapper.ModelMapper;
import org.springboot.dto.UserResponse;
import org.springboot.entity.User;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile()
	{
		User LoggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse =  mapper.map(LoggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
}
