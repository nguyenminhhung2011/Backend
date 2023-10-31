package com.FitnessApp.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	private Long id;
	private String name;
	private String description;
	private String exerciseCategory;
	private double caloriesPerMinute;
	private String videoUrl;

	@ManyToMany
	private List<Steps> steps;

//	private List<String> muscleGroups ;
	private int sets;
	private int reps;
}
