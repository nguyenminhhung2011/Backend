package com.FitnessApp.DTO.Response;

import java.util.Date;

import com.FitnessApp.Model.WorkoutPlan;
import com.FitnessApp.Utils.Enums.PlanType;
import lombok.Data;
import lombok.Getter;

@Getter
public class WorkoutPlanResponse {
	// Getters and setters
	// ID
	private Long id;
	// Name
	private String name;
	// Description
	private String description;
	// Start Date
	private Date startDate;
	// End Date
	private Date endDate;
	// Plan Type
	private PlanType type;

	// Constructors
	public WorkoutPlanResponse() {
		// Default constructor
	}

	public WorkoutPlanResponse(Long id, String name, String description, Date startDate, Date endDate, PlanType type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setType(PlanType type) {
		this.type = type;
	}

	public static WorkoutPlanResponse getFrom(WorkoutPlan workoutPlan) {
		WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse();
		workoutPlanResponse.setId(workoutPlan.getId());
		workoutPlanResponse.setName(workoutPlan.getName());
		workoutPlanResponse.setDescription(workoutPlan.getDescription());
		workoutPlanResponse.setStartDate(workoutPlan.getStartDate());
		workoutPlanResponse.setEndDate(workoutPlan.getEndDate());
		workoutPlanResponse.setType(workoutPlan.getType());
		return workoutPlanResponse;
	}

	// ... (các getters và setters)
}
