package com.FitnessApp.Model;

import java.util.List;

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

	@OneToMany
	private List<DailyWorkout> dailyWorkouts;
}
