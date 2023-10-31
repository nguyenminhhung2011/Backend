package com.FitnessApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
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
    public List<ExerciseDTO> exerciseDTOList;
}
