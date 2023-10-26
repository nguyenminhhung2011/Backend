package com.FitnessApp.Model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class WorkoutPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indica
	private Long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private String planCategory;

	@OneToMany
	private List<DailyWorkout> dailyReports;

	// Constructors, getters, and setters...

	public WorkoutPlan() {
		// Default constructor
	}

	public WorkoutPlan(String title, Date startDate, Date endDate, String planCategory,
			List<DailyWorkout> dailyReports) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.planCategory = planCategory;
		this.dailyReports = dailyReports;
	}

	// Getters and setters for the properties...
}
