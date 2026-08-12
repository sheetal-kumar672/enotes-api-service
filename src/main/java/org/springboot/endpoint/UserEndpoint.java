package org.springboot.endpoint;

import org.springboot.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "User",description  = "Authentication User Operation APIs")
@RequestMapping("/api/user")
public interface UserEndpoint {
	
	@Operation(summary = "Get User Profile",tags = {"User"},description = "Get User Profile")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	
	@Operation(summary = "User Account Password Change",tags = {"User"},description = "User Account Password Change")
	@PostMapping("/change-pswd")
	public ResponseEntity<?> chnagePassword(@RequestBody PasswordChangeRequest passwordRequest);

}
