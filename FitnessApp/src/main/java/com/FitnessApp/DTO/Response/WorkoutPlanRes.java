package com.FitnessApp.DTO.Response;

import java.util.Date;

import com.FitnessApp.Enums.PlanType;
import com.FitnessApp.Model.WorkoutPlan;

public class WorkoutPlanRes {
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private PlanType type;

	// Constructors
	public WorkoutPlanRes() {
		// Default constructor
	}

	public WorkoutPlanRes(Long id, String name, String description, Date startDate, Date endDate, PlanType type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	// Getters and setters
	// ID
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Description
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Start Date
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	// End Date
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// Plan Type
	public PlanType getType() {
		return type;
	}

	public void setType(PlanType type) {
		this.type = type;
	}

	public static WorkoutPlanRes getFrom(WorkoutPlan workoutPlan) {
		WorkoutPlanRes workoutPlanRes = new WorkoutPlanRes();
		workoutPlanRes.setId(workoutPlan.getId());
		workoutPlanRes.setName(workoutPlan.getName());
		workoutPlanRes.setDescription(workoutPlan.getDescription());
		workoutPlanRes.setStartDate(workoutPlan.getStartDate());
		workoutPlanRes.setEndDate(workoutPlan.getEndDate());
		workoutPlanRes.setType(workoutPlan.getType());
		return workoutPlanRes;
	}

	// ... (các getters và setters)
}
