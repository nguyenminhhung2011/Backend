package com.FitnessApp.Model;

import com.FitnessApp.DTO.Views.ExerciseViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Steps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
    private Long id;

    @JsonView(value = {ExerciseViews.Detail.class})
    private int step;

    @JsonView(value = {ExerciseViews.Detail.class})
    private String instruction;

    public Steps(int step,String instruction) {
        this.step = step;
        this.instruction = instruction;
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id",referencedColumnName = "id")
    private Exercise exercise;
}
