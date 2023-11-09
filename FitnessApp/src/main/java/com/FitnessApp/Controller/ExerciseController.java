package com.FitnessApp.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.Utils.JwtTokenUtils;
import com.FitnessApp.Model.User;
import com.FitnessApp.Service.User.UserService;
import com.FitnessApp.Service.ExcerciseService.ExerciseService;

@AllArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

	private final ExerciseService eService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getExerciseById(@RequestParam String t) {

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "HƯng rất đẹp trai", ""));

	}

}
