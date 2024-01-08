package com.fitlife.app.dataclass.dto.activitiesLog;

import com.fitlife.app.dataclass.dto.WorkoutPlanDTO;
import lombok.Data;

@Data
public class ActivitiesLogDTO {
    private Long id;
    private Long time;
    private WorkoutPlanDTO workoutPlan;
}
