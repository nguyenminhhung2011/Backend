package com.FitnessApp.Model;

import java.util.List;

import com.FitnessApp.DTO.User.UserProfileDTO;
import com.FitnessApp.Enums.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkoutPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indica
	private Long id;
	private String name;
	private String description;
	private Long startDate;
	private Long endDate;

	@Enumerated(EnumType.STRING)
	private PlanType type;

	@OneToMany(
		mappedBy = "workoutPlan",
		orphanRemoval = true,
		cascade = CascadeType.ALL
	)
	private List<DailyWorkout> dailyWorkouts;

	@OneToMany(
		mappedBy = "workoutPlan",
		orphanRemoval = true,
		cascade = CascadeType.ALL
	)
	private List<ActivitiesLog> activitiesLogs;

	@ManyToOne
	@JoinColumn(name = "user_profile_id", referencedColumnName = "id")
	private UserProfile userProfile;
}
