package com.FitnessApp.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.FitnessApp.DTO.Request.FetchExerciseRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<?> getExerciseById(@PathVariable Long id) {
		return ResponseEntity.ok(eService.findById(id));
	}
}
