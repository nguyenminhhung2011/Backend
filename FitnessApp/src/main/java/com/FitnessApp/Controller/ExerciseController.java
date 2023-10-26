package com.FitnessApp.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FitnessApp.DTO.ResponseObject;
import com.FitnessApp.Service.ExcerciseService.ExerciseService;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class ExerciseController {

	@Autowired
	ExerciseService eService;

	@GetMapping("/")
	public ResponseEntity<?> addExercise(@RequestParam String t)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "HƯng rất đẹp trai", ""));

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
