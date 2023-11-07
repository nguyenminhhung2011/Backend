package com.FitnessApp.Model;

import java.util.List;

import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

	@OneToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	@OneToMany(mappedBy = "userProfile")
	private List<ActivitiesLog> activitiesLogs; //History

	@ManyToMany(mappedBy = "favoriteUser")
	private List<Exercise> favoriteExercises;

	@OneToMany(mappedBy = "userProfile")
	private List<WorkoutPlan> workoutPlans;
}
