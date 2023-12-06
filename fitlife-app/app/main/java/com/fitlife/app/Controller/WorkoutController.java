package com.fitlife.app.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fitlife.app.Exceptions.AppException.NotFoundException;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Repository.User.UserProfileRepository;
import com.fitlife.app.Utils.Enums.PlanType;
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
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Model.AISupport;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.WorkoutPlan;
import com.fitlife.app.Service.DailyWorkout.DailyServiceImpl;
import com.fitlife.app.Service.WorkoutService.WorkoutServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

	final WorkoutServiceImpl workoutService;

	final UserProfileRepository userProfileRepository;

	final JwtTokenUtils jwtHelper;

	final DailyServiceImpl dailySV;

	public WorkoutController(WorkoutServiceImpl workoutService, UserProfileRepository userProfileRepository, JwtTokenUtils jwtHelper, DailyServiceImpl dailySV) {
		this.workoutService = workoutService;
		this.userProfileRepository = userProfileRepository;
		this.jwtHelper = jwtHelper;
		this.dailySV = dailySV;
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<WorkoutPlan> res = workoutService.findAll();
		List<WorkoutPlanResponse> wplResponse = new ArrayList<>();

		if (res.isEmpty())
			return ResponseEntity.ok().body(new ResponseObject("ok", "there is no wplan", null));
		else {
			for (WorkoutPlan workoutPlanRes : res) {
				wplResponse.add(WorkoutPlanResponse.getFrom(workoutPlanRes));
			}
			return ResponseEntity.ok().body(new ResponseObject("ok", "All workoutplan", wplResponse));

		}
	}

	@GetMapping("")
	public ResponseEntity<?> getMyWorkoutPlan(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);

		Long idUser = jwtHelper.getUserIdFromJWT(token);

		// Validate or process data if needed before creating the entity
		List<WorkoutPlan> res = workoutService.getWorkoutPlansByUserProfileId(idUser);
		List<WorkoutPlanResponse> wplResponse = new ArrayList<>();

		if (res.isEmpty())
			return ResponseEntity.ok().body(new ResponseObject("ok", "there is no wplan", null));
		else {
			for (WorkoutPlan workoutPlanRes : res) {
				wplResponse.add(WorkoutPlanResponse.getFrom(workoutPlanRes));
			}
			return ResponseEntity.ok().body(new ResponseObject("ok", "All workoutplan", wplResponse));
		}

	}

	@PostMapping("")
	public ResponseEntity<?> createWorkoutPlan(@RequestBody WorkoutPlanReq workoutPlanDTO, HttpServletRequest request) {

		try {
			String token = request.getHeader("Authorization").substring(7);
//			String token = "Sdsdf";
			Long idUser = jwtHelper.getUserIdFromJWT(token);
			// Validate or process data if needed before creating the entity

			WorkoutPlan workoutPlan = new WorkoutPlan();

			workoutPlan.setName(workoutPlanDTO.getName());
			workoutPlan.setDescription(workoutPlanDTO.getDescription());
			workoutPlan.setStartDate(workoutPlanDTO.getStartDate());
			workoutPlan.setEndDate(workoutPlanDTO.getEndDate());
			workoutPlan.setType(PlanType.valueOf(workoutPlanDTO.getType()));

			Optional<UserProfile> prf = userProfileRepository.findById(idUser);

			if (prf.isEmpty()) {
				throw new NotFoundException("User not found");
			}

			workoutPlan.setUserProfile(prf.get());

			if (workoutPlanDTO.getType().equals("AI")) {
				AISupport ai = new AISupport();
				ai.setFitnessGoal(workoutPlanDTO.getFitnessGoal());
				ai.setFitnessLevelCurrent(workoutPlanDTO.getFitnessLevelCurrent());
				ai.setPreference(workoutPlanDTO.getPreference());
				ai.setWorkoutplan(workoutPlan);
				workoutPlan.setAiSupport(ai);
			}

			workoutService.save(workoutPlan);
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "created success", WorkoutPlanResponse.getFrom(workoutPlan)));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to create Workout Plan\"", null));

		}
	}

	@PostMapping("/daily")
	public ResponseEntity<?> createDailyPlan(@RequestBody DailyWorkoutReq req, @RequestParam("id") String id) {

		try {

			WorkoutPlan workoutPlan = workoutService.findById(Long.parseLong(id));

			DailyWorkout newDaily;
			newDaily = DailyWorkout.builder().name(req.getName()).description(req.getDescription())
					.breakTime(req.getBreakTime()).caloTarget(req.getCaloTarget()).time(req.getTime())
					.execPerRound(req.getExecPerRound()).numberRound(req.getNumberRound())
					.timeForEachExe(req.getTimeForEachExe()).workoutDuration(req.getWorkoutDuration()).build();
			newDaily.setWorkoutPlan(workoutPlan);

			List<DailyWorkout> curentDaily = workoutPlan.getDailyWorkouts();

			if (curentDaily == null) {
				curentDaily = new ArrayList<>();

			}
			curentDaily.add(newDaily);
			workoutPlan.setDailyWorkouts(curentDaily);
			workoutService.save(workoutPlan);
			req.setId(newDaily.getId());
			return ResponseEntity.ok().body(new ResponseObject("ok", "created success", req));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to create Workout Plan\"", null));

		}
	}

	@GetMapping("/daily")
	public ResponseEntity<?> getAllDailyPlan(@RequestParam("id") String id) {

		try {

			WorkoutPlan workoutPlan = workoutService.findById(Long.parseLong(id));

			List<DailyWorkout> curentDaily = workoutPlan.getDailyWorkouts();
			if (curentDaily == null) {
				return ResponseEntity.ok().body(new ResponseObject("ok", "There is no schedule yet", null));

			}
			for (DailyWorkout dailyWorkout : curentDaily) {
				dailyWorkout.setWorkoutPlan(null);
			}

			return ResponseEntity.ok().body(new ResponseObject("ok", "all daily workout", curentDaily));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to create Workout Plan\"", null));

		}
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

		dailySV.delete(Long.parseLong(id));
		return ResponseEntity.ok().body(new ResponseObject("ok", "deleted successfully\"", null));

	}
}
