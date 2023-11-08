package com.FitnessApp.Controller;

import com.FitnessApp.Service.Authentication.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.AuthRequest;
import com.FitnessApp.DTO.RegistrationRequest;
import com.FitnessApp.DTO.ResponseObject;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class AuthenticationController {
	private final IAuthService authService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

		try {
			final AuthResponse authResponse = authService.login(authRequest);
			return ResponseEntity.ok(authResponse);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(new ResponseObject("failed", "Đăng nhập không thành công", ex.getMessage()));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
		try {
			return authService.register(registrationRequest);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken){
		return authService.refreshToken(refreshToken);
	}
}