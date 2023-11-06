package com.FitnessApp.DTO;

import com.FitnessApp.Model.Steps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String name;

    private String description;
    private String exerciseCategory;
    private double caloriesPerMinute;
    private String videoUrl;
    private List<Steps> steps;

    private int sets;
    private int reps;
}