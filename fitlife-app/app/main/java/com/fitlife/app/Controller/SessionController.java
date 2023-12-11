package com.fitlife.app.Controller;


import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Service.Session.ISessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitlife.app.DTO.Request.CustomExerciseRequest;
import com.fitlife.app.DTO.Request.SessionRequest;
import com.fitlife.app.DTO.Response.ResponseObject;

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

	@PostMapping("/add/exercise")
	public ResponseEntity<?> createCustomExercise(
			@RequestBody CustomExerciseRequest request,
			@RequestParam("id") String id
	) throws BadRequestException {
		return ResponseEntity.ok(sessionService.createCustomExercise(request, id));
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAllSession(@RequestParam("id") String id) throws BadRequestException {
		return ResponseEntity.ok(sessionService.getAllSession(id));
	}

	@GetMapping("/up-coming")
	public ResponseEntity<?> getUpComingSesssion() throws BadRequestException{
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
