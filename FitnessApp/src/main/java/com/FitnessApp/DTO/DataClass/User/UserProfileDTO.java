package com.FitnessApp.DTO.DataClass.User;

import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import com.FitnessApp.Model.ActivitiesLog;
import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Model.WorkoutPlan;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long profileId;
    private double weight;
    private double height;
    private String phone;
    private String level;
    private String currentPlan; // Current workout plan that working on
    private ThemeStatus themeStatus;
    private Gender gender;
    private Frequency frequency;
    private List<ActivitiesLog> activitiesLogs; //History
    private List<Exercise> favoriteExercises;
    private List<WorkoutPlan> workoutPlans;
}
