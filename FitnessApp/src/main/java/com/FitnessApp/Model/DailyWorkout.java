package com.FitnessApp.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class DailyWorkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCaloTarget() {
		return caloTarget;
	}

	public void setCaloTarget(double caloTarget) {
		this.caloTarget = caloTarget;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public List<Exercise> getExercisesCompleted() {
		return exercisesCompleted;
	}

	public void setExercisesCompleted(List<Exercise> exercisesCompleted) {
		this.exercisesCompleted = exercisesCompleted;
	}

	private String name;
	private String description;
	private double caloTarget;
	private int time; // Thời gian trong phút (1h = 60 phút)

	@OneToMany
	private List<Exercise> exercisesCompleted;

	// Constructors, getters, and setters...

	public DailyWorkout() {
		// Default constructor
	}

	public DailyWorkout(String name, String description, double caloTarget, int time,
			List<Exercise> exercisesCompleted) {
		this.name = name;
		this.description = description;
		this.caloTarget = caloTarget;
		this.time = time;
		this.exercisesCompleted = exercisesCompleted;
	}

	// Getters and setters for the properties...
}
