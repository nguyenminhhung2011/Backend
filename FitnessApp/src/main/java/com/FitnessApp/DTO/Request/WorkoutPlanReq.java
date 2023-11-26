package com.FitnessApp.DTO.Request;

import java.util.Date;

import lombok.Data;

@Data
public class WorkoutPlanReq {
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private String type;
	private String preference;
	private String fitnessLevelCurrent;
	private String fitnessGoal;

}
