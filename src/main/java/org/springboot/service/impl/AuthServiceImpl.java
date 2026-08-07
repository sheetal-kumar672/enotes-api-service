package org.springboot.service.impl;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springboot.config.security.CustomUserDetails;
import org.springboot.dto.EmailRequest;
import org.springboot.dto.LoginRequest;
import org.springboot.dto.LoginResponse;
import org.springboot.dto.UserRequest;
import org.springboot.dto.UserResponse;
import org.springboot.entity.AccountStatus;
import org.springboot.entity.Role;
import org.springboot.entity.User;
import org.springboot.repository.RoleRepository;
import org.springboot.repository.UserRepository;
import org.springboot.service.JwtService;
import org.springboot.service.AuthService;
import org.springboot.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;  
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;

	@Override
	public Boolean register(UserRequest userDto,String url) throws Exception {
		
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto, user);
		
		AccountStatus status = AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		
		user.setStatus(status);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveUser = userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser))
		{
			// send email
			emailSendForRegister(saveUser,url);
			return true;
		}
		return false;
	}

	private void emailSendForRegister(User saveUser, String url) throws Exception {
		
		String message = "Hi,<b>[[username]]</b>"
				+ "<br> Your account register successfully.<br>"
				+"<br> Click the below link verify your account <br>"
				+"<a href = '[[url]]'> Click Here </a> <br><br>"
				+"Thanks,<br>Enotes.com"
				;
		
		message = message.replace("[[username]]", saveUser.getFirstName());
		message = message.replace("[[url]]", url+"/api/home/verify?uid="+saveUser.getId()+"&code="+saveUser.getStatus().getVerificationCode());
		
		EmailRequest emailRequest=EmailRequest.builder()
				.to(saveUser.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created success")
				.message(message)
				.build();
		
		emailService.sendEmail(emailRequest);
	}

	private void setRole(UserRequest userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
		
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated())
		{

		CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			
			String token = jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse loginResponse = LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token)
					.build();
			return loginResponse;
		}
		return null;
	}

}
