package com.FitnessApp.DTO.Request;

import java.sql.Timestamp;
import java.util.Date;

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
