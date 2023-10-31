package com.FitnessApp.Model;

import java.util.List;

import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double weight;
	private double height;
	private String phone;
	private String level;
	private String currentPlan; // Current workout plan that working on

	@Enumerated(EnumType.STRING)
	private ThemeStatus themeStatus;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Frequency frequency;

	@OneToMany
	private List<ActivitiesLog> activitiesLogs; //History

	@ManyToMany
	private List<Exercise> favoriteExercises;

	@OneToMany
	private List<WorkoutPlan> workoutPlans;
}
