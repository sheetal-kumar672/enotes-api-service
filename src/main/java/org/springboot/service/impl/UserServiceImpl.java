package org.springboot.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springboot.dto.UserDto;
import org.springboot.entity.Role;
import org.springboot.entity.User;
import org.springboot.repository.RoleRepository;
import org.springboot.repository.UserRepository;
import org.springboot.service.UserService;
import org.springboot.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean register(UserDto userDto) {
		
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto, user);
		
		User saveUser = userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser))
		{
			return true;
		}
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
		
	}

}
