package com.fitlife.app.dataClass.request;

import lombok.Data;

@Data
public class WorkoutPlanRequest {
	private String name;
	private String description;
	private long startDate;
	private long endDate;
	private String type;
	private String preference;
	private String fitnessLevelCurrent;
	private String fitnessGoal;

}
