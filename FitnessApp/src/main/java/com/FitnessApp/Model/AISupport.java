package com.FitnessApp.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aisupport")
public class AISupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fitness_goal")
	private String fitnessGoal;

	@Column(name = "fitness_level_current")
	private String fitnessLevelCurrent;

	@Column(name = "preference")
	private String preference;

	@OneToOne
	WorkoutPlan workoutplan;

	public WorkoutPlan getWorkoutplan() {
		return workoutplan;
	}

	public void setWorkoutplan(WorkoutPlan workoutplan) {
		this.workoutplan = workoutplan;
	}

	// Constructors
	public AISupport() {
		// Default constructor
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFitnessGoal() {
		return fitnessGoal;
	}

	public void setFitnessGoal(String fitnessGoal) {
		this.fitnessGoal = fitnessGoal;
	}

	public String getFitnessLevelCurrent() {
		return fitnessLevelCurrent;
	}

	public void setFitnessLevelCurrent(String fitnessLevelCurrent) {
		this.fitnessLevelCurrent = fitnessLevelCurrent;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}
}
