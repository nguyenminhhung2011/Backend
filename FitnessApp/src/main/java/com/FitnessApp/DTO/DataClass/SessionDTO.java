package com.FitnessApp.DTO.DataClass;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SessionDTO {
    private Long id;
    private DailyWorkoutDTO dailyWorkouts;
    private List<ExerciseDTO> exercises;
}
