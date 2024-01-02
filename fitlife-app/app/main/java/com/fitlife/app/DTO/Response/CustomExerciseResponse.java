package com.fitlife.app.DTO.Response;

import com.fitlife.app.DTO.DataClass.ExerciseDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExerciseResponse {
    private Long id;
    private int time;
    private int weight;
    private int rep;
    private int calories;
    private String difficulty;
    private ExerciseDTO exercise;
}
