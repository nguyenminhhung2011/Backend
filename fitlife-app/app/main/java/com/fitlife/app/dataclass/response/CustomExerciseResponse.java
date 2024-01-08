package com.fitlife.app.dataclass.response;

import com.fitlife.app.dataclass.dto.ExerciseDTO;
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
