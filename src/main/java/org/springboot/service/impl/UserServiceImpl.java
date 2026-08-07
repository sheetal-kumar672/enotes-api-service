package org.springboot.service.impl;

import org.jspecify.annotations.Nullable;
import org.springboot.dto.PasswordChangeRequest;
import org.springboot.entity.User;
import org.springboot.repository.UserRepository;
import org.springboot.service.UserService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public void chnagePassword(PasswordChangeRequest passwordRequest) {
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		if(!passwordEncoder.matches( passwordRequest.getOldPassword(),loggedInUser.getPassword()))
		{
		    throw new IllegalArgumentException("old Password is incorrect");	
		}
	
		String encodePassword = passwordEncoder.encode(passwordRequest.getNewPassword());
		loggedInUser.setPassword(encodePassword);
		userRepo.save(loggedInUser);
		
	}

}
