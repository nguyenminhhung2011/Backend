package com.fitlife.app.controller;

import com.fitlife.app.dataClass.request.GetChartRequest;
import com.fitlife.app.dataClass.request.SearchWorkoutPlanRequest;
import com.fitlife.app.exceptions.appException.BadRequestException;
import com.fitlife.app.security.model.CurrentUser;
import com.fitlife.app.security.model.FitLifeUserDetail;
import com.fitlife.app.service.dailyWorkout.IDailyService;
import com.fitlife.app.service.workout.IWorkoutService;
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
import com.fitlife.app.dataClass.request.DailyWorkoutRequest;
import com.fitlife.app.dataClass.request.WorkoutPlanRequest;
import com.fitlife.app.dataClass.response.ResponseObject;


@RestController
@RequestMapping("/workout")
public class  WorkoutController {

	final IWorkoutService workoutService;

	final IDailyService dailyService;

	public WorkoutController(IWorkoutService workoutService, IDailyService dailyService) {
		this.workoutService = workoutService;
		this.dailyService = dailyService;
	}


	@GetMapping("/get-chart-view")
	public ResponseEntity<?> getChartView(
			@RequestBody GetChartRequest getChartRequest,
			@CurrentUser FitLifeUserDetail ctx
	){
		return ResponseEntity.ok(workoutService.getChartView(getChartRequest, ctx.getId()));
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(workoutService.getAll());
	}

	@GetMapping("")
	public ResponseEntity<?> getMyWorkoutPlan(@CurrentUser FitLifeUserDetail ctx) {
		return ResponseEntity.ok(workoutService.getMyWorkoutPlan(ctx.getId()));

	}

	@PostMapping("")
	public ResponseEntity<?> createWorkoutPlan(@RequestBody WorkoutPlanRequest workoutPlanDTO,
											   @CurrentUser FitLifeUserDetail ctx) throws BadRequestException {
		return ResponseEntity.ok(workoutService.createWorkoutPlan(workoutPlanDTO,ctx.getId() ));

	}

	@PostMapping("/daily")
	public ResponseEntity<?> createDailyPlan(@RequestBody DailyWorkoutRequest req, @RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(workoutService.createDailyPlan(req,Long.parseLong(id) ));
	}

	@GetMapping("/daily")
	public ResponseEntity<?> getAllDailyPlan(@RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(workoutService.getAllDailyPlan(id));

	}
	@GetMapping("/active")
	public ResponseEntity<?> getActiveWorkoutPlan(@RequestParam("time") String time) throws BadRequestException {
		return ResponseEntity.ok(workoutService.getActiveWorkoutPlan(Long.parseLong(time)));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Object>> searchWorkoutPlans(
			@CurrentUser FitLifeUserDetail ctx,
			@RequestBody SearchWorkoutPlanRequest searchWorkoutPlanRequest
			) {

		Pageable pageable = PageRequest.of(searchWorkoutPlanRequest.page(), searchWorkoutPlanRequest.size());
		Page<Object> workoutPlans = workoutService.searchWorkoutPlans(
				ctx.getId(),
				searchWorkoutPlanRequest.name(),
				searchWorkoutPlanRequest.startDate(),
				searchWorkoutPlanRequest.endDate(), pageable);

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
