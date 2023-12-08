package com.fitlife.app.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Service.DailyWorkout.IDailyService;
import com.fitlife.app.Service.Workout.IWorkoutService;
import com.fitlife.app.Utils.Jwt.JwtTokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fitlife.app.DTO.Request.DailyWorkoutReq;
import com.fitlife.app.DTO.Request.WorkoutPlanReq;
import com.fitlife.app.DTO.Response.ResponseObject;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

	final IWorkoutService workoutService;


	final JwtTokenUtils jwtHelper;

	final IDailyService dailyService;

	public WorkoutController(IWorkoutService workoutService, JwtTokenUtils jwtHelper, IDailyService dailyService) {
		this.workoutService = workoutService;
		this.jwtHelper = jwtHelper;
		this.dailyService = dailyService;
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(workoutService.getAll());
	}

	@GetMapping("")
	public ResponseEntity<?> getMyWorkoutPlan(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		Long idUser = jwtHelper.getUserIdFromJWT(token);
		return ResponseEntity.ok(workoutService.getWorkoutPlansByUserProfileId(idUser));

	}

	@PostMapping("")
	public ResponseEntity<?> createWorkoutPlan(@RequestBody WorkoutPlanReq workoutPlanDTO, HttpServletRequest request) throws BadRequestException {
		String token = request.getHeader("Authorization").substring(7);
		Long idUser = jwtHelper.getUserIdFromJWT(token);
		return ResponseEntity.ok(workoutService.createWorkoutPlan(workoutPlanDTO, idUser));

	}

	@PostMapping("/daily")
	public ResponseEntity<?> createDailyPlan(@RequestBody DailyWorkoutReq req, @RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(workoutService.createDailyPlan(req,Long.parseLong(id) ));
	}

	@GetMapping("/daily")
	public ResponseEntity<?> getAllDailyPlan(@RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(workoutService.getAllDailyPlan(id));

	}

	@GetMapping("/search")
	public ResponseEntity<Page<Object>> searchWorkoutPlans(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);

		Long idUser = jwtHelper.getUserIdFromJWT(token);

		Pageable pageable = PageRequest.of(page, size);
		Page<Object> workoutPlans = workoutService.searchWorkoutPlans(idUser, name, startDate, endDate, pageable);

		return ResponseEntity.ok(workoutPlans);

	}

	@PostMapping("/delete")
	public ResponseEntity<Object> deleteWorkoutplan(@RequestParam("id") String id) {

		workoutService.delete(Long.parseLong(id));
		return ResponseEntity.ok().body(new ResponseObject("ok", "deleted successfully\"", null));

	}

	@PostMapping("/daily/delete")
	public ResponseEntity<Object> deleteDailyWorkout(@RequestParam("id") String id) {

		dailyService.delete(Long.parseLong(id));
		return ResponseEntity.ok().body(new ResponseObject("ok", "deleted successfully\"", null));

	}
}
