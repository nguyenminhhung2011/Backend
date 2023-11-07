package com.FitnessApp.DTO;

import lombok.Data;

@Data
public class GymerRegistrationRequest {
	private String fullName;
	private String username;
	private String password;
	private int age;
	private String gender;
}
