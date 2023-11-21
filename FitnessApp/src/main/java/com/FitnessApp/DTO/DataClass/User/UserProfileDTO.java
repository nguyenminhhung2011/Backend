package com.FitnessApp.DTO.DataClass.User;

import com.FitnessApp.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.FitnessApp.DTO.DataClass.ExerciseDTO;
import com.FitnessApp.DTO.DataClass.WorkoutPlanDTO;
import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import com.FitnessApp.Model.ActivitiesLog;
import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Model.WorkoutPlan;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private double weight;
    private double height;
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
