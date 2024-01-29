package com.fitlife.app.dataClass.dto.activitiesLog;

import com.fitlife.app.dataClass.dto.WorkoutPlanDTO;
import lombok.Data;

@Data
public class ActivitiesLogDTO {
    private Long id;
    private Long time;
    private WorkoutPlanDTO workoutPlan;
}
