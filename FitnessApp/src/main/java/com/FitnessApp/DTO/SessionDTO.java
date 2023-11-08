package com.FitnessApp.DTO;

import com.FitnessApp.Model.DailyWorkout;
import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Model.Session;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SessionDTO {
    private Long id;
    private DailyWorkoutDTO dailyWorkouts;
    private List<ExerciseDTO> exercises;
}
