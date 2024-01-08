package com.fitlife.app.controller;


import com.fitlife.app.dataclass.request.session.UpdateSettingSessionRequest;
import com.fitlife.app.exceptions.AppException.BadRequestException;
import com.fitlife.app.service.Session.ISessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitlife.app.dataclass.request.CustomExerciseRequest;
import com.fitlife.app.dataclass.request.session.SessionRequest;
import com.fitlife.app.dataclass.response.ResponseObject;

@RestController
@RequestMapping("/session")
public class SessionController {

	private final ISessionService sessionService;

	public SessionController(ISessionService sessionService){
		this.sessionService = sessionService;
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteSession(@RequestParam("id") String id) {
		sessionService.delete(Long.parseLong(id));
		return ResponseEntity.ok().body(new ResponseObject("ok", "Delete successfully\"", null));
	}

	@PutMapping("/update-setting-session")
	public ResponseEntity<?> updateSettingSession(
			@RequestBody UpdateSettingSessionRequest request,
			@RequestParam("id") String id
	) throws BadRequestException {
		return ResponseEntity.ok(sessionService.updateSettingSession(request, Long.parseLong(id)));
	}

	@PostMapping("/add/exercise")
	public ResponseEntity<?> createCustomExercise(
			@RequestBody CustomExerciseRequest request,
			@RequestParam("id") String id
	) throws BadRequestException {
		return ResponseEntity.ok(sessionService.createCustomExercise(request, id));
	}

	@PostMapping("/complete-session")
	public ResponseEntity<?> completeSession(
			@RequestParam("id") String id
	) throws BadRequestException {
		return ResponseEntity.ok(sessionService.completeSession(id));
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAllSession(@RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(sessionService.getAllSession(id));
	}

	@GetMapping("/up-coming")
	public ResponseEntity<?> getUpComingSession() throws BadRequestException{
		return ResponseEntity.ok(sessionService.getUpComingSession());
	}

	@GetMapping
	public ResponseEntity<?> getSessionById(@RequestParam("id") String id) {
		return ResponseEntity.ok(sessionService.getSessionById(id));
	}

	@PostMapping("/add")
	public ResponseEntity<?> createSession(@RequestBody SessionRequest req) throws BadRequestException {
		return ResponseEntity.ok(sessionService.createSession(req));
	}
}
