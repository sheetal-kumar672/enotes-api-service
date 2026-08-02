package org.springboot.service.impl;


import org.springboot.entity.AccountStatus;
import org.springboot.entity.User;
import org.springboot.exception.ResourceNotFoundException;
import org.springboot.exception.SuccessException;
import org.springboot.repository.UserRepository;
import org.springboot.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("invalid user"));
		
		if(user.getStatus().getVerificationCode()==null)
		{
			throw new SuccessException("Account already verified");
		}
		
		
		if(user.getStatus().getVerificationCode().equals(verificationCode))
		{
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepo.save(user);
			
			return true;
		}
		
		return false;
	}

}
