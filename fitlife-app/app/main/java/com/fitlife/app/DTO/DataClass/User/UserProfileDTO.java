package com.fitlife.app.DTO.DataClass.User;

import com.fitlife.app.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.fitlife.app.DTO.DataClass.ExerciseDTO;
import com.fitlife.app.DTO.DataClass.NewsHealthDTO;
import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import com.fitlife.app.Utils.Enums.Frequency;
import com.fitlife.app.Utils.Enums.Gender;
import com.fitlife.app.Utils.Enums.ThemeStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private boolean isCreated;
    private double weight;
    private double height;
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
