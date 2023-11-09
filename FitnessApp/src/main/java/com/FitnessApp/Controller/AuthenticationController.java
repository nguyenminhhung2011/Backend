package com.FitnessApp.Controller;

import com.FitnessApp.Service.Authentication.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FitnessApp.DTO.Response.AuthResponse;
import com.FitnessApp.DTO.Request.AuthRequest;
import com.FitnessApp.DTO.Request.RegistrationRequest;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class AuthenticationController {
	private final IAuthService authService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) throws AuthenticationException {
		final AuthResponse authResponse = authService.login(authRequest);
		return ResponseEntity.ok(authResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register( @Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {
	return authService.register(registrationRequest);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken){
		return authService.refreshToken(refreshToken);
	}
}