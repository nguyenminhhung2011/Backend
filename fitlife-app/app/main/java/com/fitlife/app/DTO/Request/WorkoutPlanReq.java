package com.fitlife.app.DTO.Request;

import lombok.Data;

@Data
public class WorkoutPlanReq {
	private String name;
	private String description;
	private long startDate;
	private long endDate;
	private String type;
	private String preference;
	private String fitnessLevelCurrent;
	private String fitnessGoal;

}
