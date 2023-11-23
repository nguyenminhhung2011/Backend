package com.FitnessApp.DTO.DataClass.ActivitiesLog;

import com.FitnessApp.DTO.DataClass.WorkoutPlanDTO;
import com.FitnessApp.Model.WorkoutPlan;
import lombok.Data;

@Data
public class ActivitiesLogDTO {
    private Long id;
    private Long time;
    private WorkoutPlanDTO workoutPlan;
}
