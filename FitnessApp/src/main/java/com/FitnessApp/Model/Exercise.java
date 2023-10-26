package com.FitnessApp.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs

	private Long id;
	private String name;
	private String exerciseCategory;
	private String description;
	private double caloriesPerMinute;
	private int steps;
	private String videoUrl;
	private List<String> muscleGroups;
	private int sets;
	private int reps;

	// Constructors, getters, and setters...

	public Exercise() {
		// Default constructor
	}

	public Exercise(String name, String exerciseCategory, String description, double caloriesPerMinute, int steps,
			String videoUrl, List<String> muscleGroups, int sets, int reps) {
		this.name = name;
		this.exerciseCategory = exerciseCategory;
		this.description = description;
		this.caloriesPerMinute = caloriesPerMinute;
		this.steps = steps;
		this.videoUrl = videoUrl;
		this.muscleGroups = muscleGroups;
		this.sets = sets;
		this.reps = reps;
	}

	// Getters and setters for the properties...
}
