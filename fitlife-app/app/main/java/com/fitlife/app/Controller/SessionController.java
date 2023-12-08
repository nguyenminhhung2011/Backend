package com.fitlife.app.Controller;

import java.util.ArrayList;
import java.util.List;

import com.fitlife.app.Service.Excercise.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitlife.app.DTO.Request.CustomExerciseDTO;
import com.fitlife.app.DTO.Request.SessionDTOReq;
import com.fitlife.app.DTO.Response.ResponseObject;
import com.fitlife.app.Model.CustomExercise;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.session.Session;
import com.fitlife.app.Model.Exercise.Exercise;
import com.fitlife.app.Service.DailyWorkout.DailyServiceImpl;
import com.fitlife.app.Service.Session.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private SessionService sessionSV;
	@Autowired
	private ExerciseService eService;

	@Autowired
	DailyServiceImpl dailySV;

	@PostMapping("/delete")
	public ResponseEntity<Object> deleteSesion(@RequestParam("id") String id) {

		sessionSV.delete(Long.parseLong(id));
		return ResponseEntity.ok().body(new ResponseObject("ok", "deleted successfully\"", null));

	}

	@PostMapping("/add/exercise")
	public ResponseEntity<?> createDailyPlan(@RequestBody CustomExerciseDTO req,
											 @RequestParam("id") String id
	) {

		try {
			Session session = sessionSV.findById(Long.parseLong(id));

			CustomExercise custom = CustomExercise.builder().rep(req.getRep()).weight(req.getWeight())
					.time(req.getTime()).difficulty(req.getDifficulty()).build();

			custom.setDateStart(req.getDateStart());

			Exercise ex = eService.findById(req.getExercise());

			custom.setExercise(ex);
			custom.setSession(session);
			List<CustomExercise> currentCustom = session.getCustomExercise();
			if (currentCustom == null) {
				currentCustom = new ArrayList<>();
			}
			currentCustom.add(custom);
			sessionSV.save(session);
			return ResponseEntity.ok().body(new ResponseObject("ok", "created success", session));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to create Workout Plan\"", null));

		}
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAllSession(@RequestParam("id") String id) {

		try {

			DailyWorkout daily = dailySV.findById(Long.parseLong(id));

			List<Session> currentSession = daily.getSessions();

			if (currentSession == null || currentSession.isEmpty()) {
				return ResponseEntity.ok().body(new ResponseObject("ok", "there is no session yet", null));

			}
			return ResponseEntity.ok().body(new ResponseObject("ok", "Finded all session ", currentSession));
		} catch (Exception e) {

			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to find session\"", null));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> createSession(@RequestBody SessionDTOReq req, @RequestParam("id") String id) {

		try {
			DailyWorkout daily = dailySV.findById(Long.parseLong(id));

			Session session = Session.builder().description(req.getDescription()).name(req.getName())
					.timePerLesson(req.getTimePerLesson()).randomMix(req.getRandomMix())
					.startWithBoot(req.getStartWithBoot()).build();
			List<Session> currentSession = daily.getSessions();

			if (currentSession == null) {
				currentSession = new ArrayList<Session>();

			}
			session.setDailyWorkouts(daily);
			currentSession.add(session);
			daily.setSessions(currentSession);
			dailySV.save(daily);
			return ResponseEntity.ok().body(new ResponseObject("ok", "created success", session));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Failed to create session\"", null));

		}
	}
}
