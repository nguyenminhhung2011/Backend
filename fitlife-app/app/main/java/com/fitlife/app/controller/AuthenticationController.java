package com.fitlife.app.controller;

import com.fitlife.app.dataclass.views.UserViews;
import com.fitlife.app.service.Authentication.IAuthService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitlife.app.dataclass.response.AuthResponse;
import com.fitlife.app.dataclass.request.AuthRequest;
import com.fitlife.app.dataclass.request.RegistrationRequest;

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

	@PostMapping("/register")
	public ResponseEntity<?> register( @Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {
		return authService.register(registrationRequest);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken){
		return authService.refreshToken(refreshToken);
	}
}