package com.FitnessApp.Controller;

import java.util.List;

import com.FitnessApp.Service.Authentication.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.AuthRequest;
import com.FitnessApp.DTO.GymerRegistrationRequest;
import com.FitnessApp.DTO.ResponseObject;
import com.FitnessApp.Utils.JwtTokenUtils;
import com.FitnessApp.Model.Role;
import com.FitnessApp.Repository.RoleRepository;
import com.FitnessApp.Model.User;
import com.FitnessApp.Service.User.UserService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class AuthenticationController {

	private  RoleRepository roleRepo;
	private  UserService uService;
	private  IAuthService authService;
	private  PasswordEncoder passEncoder;
	private  AuthenticationManager authenticationManager;
	private  JwtTokenUtils jWTTokenUtils;

	@PostMapping("/loginGymer")
	public ResponseEntity<?> loginClient(@RequestBody AuthRequest authRequest) {

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

	@PostMapping("/registerGymer")
	public ResponseEntity<?> registerGymer(@RequestBody GymerRegistrationRequest registrationRequest) {
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