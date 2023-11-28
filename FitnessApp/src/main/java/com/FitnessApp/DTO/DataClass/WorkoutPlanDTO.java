package com.FitnessApp.DTO.DataClass;

import com.FitnessApp.Utils.Enums.PlanType;
import lombok.*;

import java.util.List;

import com.FitnessApp.Enums.PlanType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlanDTO {
	private Long id;
	private String name;
	private String description;
	private long startDate;
	private long endDate;
	private PlanType type;
	private List<DailyWorkoutDTO> dailyWorkouts;

}
