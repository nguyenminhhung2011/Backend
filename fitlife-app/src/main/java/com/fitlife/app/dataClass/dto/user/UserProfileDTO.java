package com.fitlife.app.dataClass.dto.user;

import com.fitlife.app.dataClass.dto.activitiesLog.ActivitiesLogDTO;
import com.fitlife.app.dataClass.dto.ExerciseDTO;
import com.fitlife.app.dataClass.dto.NewsHealthDTO;
import com.fitlife.app.dataClass.dto.WorkoutPlanDTO;
import com.fitlife.app.utils.enums.Frequency;
import com.fitlife.app.utils.enums.Gender;
import com.fitlife.app.utils.enums.ThemeStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private boolean isCreated;
    private double weight;
    private double height;
    private Long currentPlanId;
    private String phone;
    private String level;
    private String currentPlan;
    private ThemeStatus themeStatus;
    private Gender gender;
    private Frequency frequency;
    private List<ActivitiesLogDTO> activitiesLogs;
    private List<NewsHealthDTO> favoriteNews;
    private List<ExerciseDTO> favoriteExercises;
    private List<WorkoutPlanDTO> workoutPlans;
}
