package org.springboot.endpoint;

import org.springboot.dto.PswdResetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home",description  = "All the Home APIs")
@RequestMapping("/api/home")
public interface HomeEndpoint {
	
	@Operation(summary = "Verification User Account",tags = {"Home"},description = "User Account Verification after register account")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception;
	
	
	@Operation(summary = "Send Email dor Password Reset",tags = {"Home"},description = "User Can send Email for password reset")
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest request) throws Exception;
	
	
	@Operation(summary = "Verification Password link",tags = {"Home"},description = "User verification password link")
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;
    
	
	@Operation(summary = "Reset Password",tags = {"Home"},description = "User can changes Password")
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
