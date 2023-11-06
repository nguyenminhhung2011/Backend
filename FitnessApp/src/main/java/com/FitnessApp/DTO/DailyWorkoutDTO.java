package com.FitnessApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutDTO {
    private Long id;
    private String name;
    private String description;
    private double caloTarget;
    private Long time;
    private int workoutDuration;
    private int numberRound;
    private int execPerRound;
    private int timeForEachExe;
    private int breakTime;
    private List<ExerciseDTO> exercises;
}
