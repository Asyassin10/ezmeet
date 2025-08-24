package com.example.EzMeet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EzMeet.service.EmailVerificationService;

 
@RestController
public class HomeController {
	
	
    private final EmailVerificationService verificationService;

    
	public HomeController(EmailVerificationService verificationService) {
		super();
		this.verificationService = verificationService;
	}

	@GetMapping("/home")
	public String home() {
		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodedPassword = encoder.encode(rawPassword);
		return encodedPassword;
	}
	
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        verificationService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully!");
    }

 
}
