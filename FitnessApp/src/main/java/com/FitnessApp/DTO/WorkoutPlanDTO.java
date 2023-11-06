package com.FitnessApp.DTO;

import com.FitnessApp.Enums.PlanType;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlanDTO {
    private  Long id;
    private  String name;
    private  String description;
    private  long startDate;
    private long endDate;
    private PlanType type;
    private List<DailyWorkoutDTO> dailyWorkouts;
}
