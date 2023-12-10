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
    private Date dateStart;
    private Time time;
    private String difficulty;
    private ExerciseDTO exercise;
    private int rep;
    private int weight;


}
