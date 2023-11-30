package com.FitnessApp.DTO.DataClass.User;

import com.FitnessApp.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.FitnessApp.DTO.DataClass.ExerciseDTO;
import com.FitnessApp.DTO.DataClass.WorkoutPlanDTO;
import com.FitnessApp.Utils.Enums.Frequency;
import com.FitnessApp.Utils.Enums.Gender;
import com.FitnessApp.Utils.Enums.ThemeStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private boolean isCreated;
    private double weight;

    private double height;
    private boolean isCreated;
    private String phone;

    private String level;
    private String currentPlan;
    private ThemeStatus themeStatus;
    private Gender gender;
    private Frequency frequency;
    private List<ActivitiesLogDTO> activitiesLogs;
    private List<ExerciseDTO> favoriteExercises;
    private List<WorkoutPlanDTO> workoutPlans;
}
