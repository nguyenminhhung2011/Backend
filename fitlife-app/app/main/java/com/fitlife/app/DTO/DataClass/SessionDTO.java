package com.fitlife.app.DTO.DataClass;

import com.fitlife.app.DTO.Response.CustomExerciseResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private String name;
    private String level;
    private String description;
    private Boolean startWithBoot;
    private Boolean randomMix;
    private int timePerLesson;
    private int transferTime;
    private int calcTarget;
    private int numberRound;
    private int breakTime;
    private List<CustomExerciseResponse> customExercise;
}
