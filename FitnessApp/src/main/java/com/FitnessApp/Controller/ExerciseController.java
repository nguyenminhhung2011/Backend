package com.FitnessApp.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FitnessApp.DTO.ResponseObject;
import com.FitnessApp.Security.JwtTokenHelper;
import com.FitnessApp.Security.Model.User;
import com.FitnessApp.Service.UserService;
import com.FitnessApp.Service.ExcerciseService.ExerciseService;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class ExerciseController {

	@Autowired
	ExerciseService eService;

	@Autowired
	JwtTokenHelper jwtHelper;

	@Autowired
	private UserService uService;

	@GetMapping("/api")
	public ResponseEntity<?> addExercise(@RequestParam String t)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "HƯng rất đẹp trai", ""));

	}

	@GetMapping("/token")
	public ResponseEntity<ResponseObject> testToken(@RequestHeader(value = "Authorization") String jwt) {
		try {
			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = uService.findOneByUsername(username);
			if (user != null)
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "ok", user.getUsername()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "not found data", null));
		}
		return null;
	}
//	@PostMapping("/add")
//	public ResponseEntity<?> addExercise(@RequestParam String t)
//			throws InvalidKeySpecException, NoSuchAlgorithmException {
//		
//		return "Hưng không đẹp trai";
//		return null;
//		
//	}

}
