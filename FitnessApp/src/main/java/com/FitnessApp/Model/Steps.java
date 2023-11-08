package com.FitnessApp.Model;

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
    private String step;
    private String instruction;

    public Steps(String step) {
        this.step = step;
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id",referencedColumnName = "id")
    private Exercise exercise;
}
