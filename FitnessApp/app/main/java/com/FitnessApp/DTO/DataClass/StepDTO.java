package com.FitnessApp.DTO.DataClass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StepDTO {
    private Long id;
    private  String step;
    private String instruction;
}
