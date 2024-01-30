package com.fitlife.app.dataClass.dto;


import java.util.List;

import com.fitlife.app.utils.enums.PlanType;
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
