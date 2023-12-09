package com.fitlife.app.DTO.Response;

import com.fitlife.app.DTO.DataClass.ExerciseDTO;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExerciseResponse {
    private Date dateStart;
    private Time time;
    private String difficulty;
    private Long session;
    private ExerciseDTO exercise;
    private int rep;
    private int weight;


}
