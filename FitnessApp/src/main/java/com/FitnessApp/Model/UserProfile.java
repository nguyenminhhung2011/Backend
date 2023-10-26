package com.FitnessApp.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indica
	private Long id;
	private double weight;
	private double height;
	private String level;

	@ManyToMany
	private List<Exercise> favoriteExercises;

	@OneToMany
	private List<WorkoutPlan> workoutPlans;

	// Constructors, getters, and setters...

	public UserProfile() {
		// Default constructor
	}

	public UserProfile(double weight, double height, String level, List<Exercise> favoriteExercises,
			List<WorkoutPlan> workoutPlans) {
		this.weight = weight;
		this.height = height;
		this.level = level;
		this.favoriteExercises = favoriteExercises;
		this.workoutPlans = workoutPlans;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Exercise> getFavoriteExercises() {
		return favoriteExercises;
	}

	public void setFavoriteExercises(List<Exercise> favoriteExercises) {
		this.favoriteExercises = favoriteExercises;
	}

	public List<WorkoutPlan> getWorkoutPlans() {
		return workoutPlans;
	}

	public void setWorkoutPlans(List<WorkoutPlan> workoutPlans) {
		this.workoutPlans = workoutPlans;
	}

	// Getters and setters for the properties...
}
