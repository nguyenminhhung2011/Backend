package com.fitlife.app.DTO.DataClass.ActivitiesLog;

import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import lombok.Data;

@Data
public class ActivitiesLogDTO {
    private Long id;
    private Long time;
    private WorkoutPlanDTO workoutPlan;
}
