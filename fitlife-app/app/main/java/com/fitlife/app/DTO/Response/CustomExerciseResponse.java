package com.fitlife.app.DTO.Response;

import com.fitlife.app.DTO.DataClass.ExerciseDTO;
import com.fitlife.app.DTO.DataClass.SessionDTO;
import com.fitlife.app.Model.session.Session;
import lombok.*;

import java.sql.Time;
import java.util.Date;

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
