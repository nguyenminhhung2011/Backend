package com.fitlife.app.Controller;

import java.util.Date;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Security.Model.CurrentUser;
import com.fitlife.app.Security.Model.FitLifeUserDetail;
import com.fitlife.app.Service.DailyWorkout.IDailyService;
import com.fitlife.app.Service.Workout.IWorkoutService;
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
import com.fitlife.app.DTO.Request.DailyWorkoutRequest;
import com.fitlife.app.DTO.Request.WorkoutPlanRequest;
import com.fitlife.app.DTO.Response.ResponseObject;


@RestController
@RequestMapping("/workout")
public class WorkoutController {

	final IWorkoutService workoutService;

	final IDailyService dailyService;

	public WorkoutController(IWorkoutService workoutService, IDailyService dailyService) {
		this.workoutService = workoutService;
		this.dailyService = dailyService;
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

	@GetMapping("/search")
	public ResponseEntity<Page<Object>> searchWorkoutPlans(
			@CurrentUser FitLifeUserDetail ctx,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Object> workoutPlans = workoutService.searchWorkoutPlans(ctx.getId(), name, startDate, endDate, pageable);

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
