package com.FitnessApp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FitnessApp.Service.User.UserServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	private final UserServiceImpl uService;

//    @JsonView(UserViews.Detail.class)
	@GetMapping("/{id}")
	ResponseEntity<?> getUser(@PathVariable long id) {
		return ResponseEntity.ok().body(uService.getUserById(id));
	}

	@GetMapping("/profile/{id}")
	ResponseEntity<?> getUserProfile(@PathVariable long id) {
		return ResponseEntity.ok(uService.getUserProfile(id));
	}

	@GetMapping("/name/{username}")
	ResponseEntity<?> getUserByUserName(@PathVariable String username) {
		return ResponseEntity.ok(uService.getUserByUsername(username));
	}

	@GetMapping
	ResponseEntity<?> getAllUser() {
		return ResponseEntity.ok(uService.getAllUser());
	}
}
