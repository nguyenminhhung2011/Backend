package com.fitlife.app.Controller;

import com.fitlife.app.DTO.Request.FetchExerciseRequest;
import com.fitlife.app.DTO.Request.PageRequest;
import com.fitlife.app.DTO.Views.ExerciseViews;
import com.fitlife.app.Model.Exercise.BodyPart;
import com.fitlife.app.Model.Exercise.Equipment;
import com.fitlife.app.Model.Exercise.Target;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitlife.app.Service.Excercise.ExerciseService;

@RestController
@AllArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

	private final ExerciseService eService;

	@GetMapping("/{id}")
	@JsonView(ExerciseViews.Detail.class)
	public ResponseEntity<?> getExerciseById(@PathVariable Long id) {
		return ResponseEntity.ok(eService.findById(id));
	}

	@GetMapping
	@JsonView(ExerciseViews.Summary.class)
	public ResponseEntity<?> getExercises(@RequestBody PageRequest pageRequest) {
		return ResponseEntity.ok(eService.fetchExercises(pageRequest));
	}

	@GetMapping("/search")
	@JsonView(ExerciseViews.Summary.class)
	public ResponseEntity<?> searchExercise(@RequestBody FetchExerciseRequest request){
		return ResponseEntity.ok(eService.searchExercise(request));
	}

	@GetMapping("/body-part")
	@JsonView(ExerciseViews.Summary.class)
	public ResponseEntity<?> getBodyPart() {
		return ResponseEntity.ok(eService.getListDataOf(BodyPart.class));
	}

	@GetMapping("/target")
	@JsonView(ExerciseViews.Summary.class)
	public ResponseEntity<?> getTarget() {
		return ResponseEntity.ok(eService.getListDataOf(Target.class));
	}

	@GetMapping("/equipment")
	@JsonView(ExerciseViews.Summary.class)
	public ResponseEntity<?> getEquipment() {
		return ResponseEntity.ok(eService.getListDataOf(Equipment.class));
	}
}
