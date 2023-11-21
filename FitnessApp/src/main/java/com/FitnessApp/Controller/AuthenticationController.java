package com.FitnessApp.Controller;

import com.FitnessApp.DTO.Views.UserViews;
import com.FitnessApp.Service.Authentication.IAuthService;
import com.fasterxml.jackson.annotation.JsonView;
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
	@JsonView(UserViews.Detail.class)
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) throws AuthenticationException {
		final AuthResponse authResponse = authService.login(authRequest);
		return ResponseEntity.ok(authResponse);
	}

//	@JsonView(UserViews.Detail.class)
	@PostMapping("/register")
	public ResponseEntity<?> register( @Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {
	return authService.register(registrationRequest);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken){
		return authService.refreshToken(refreshToken);
	}
}