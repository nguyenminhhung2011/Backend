package com.FitnessApp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Steps {
    @Id
    private Long id;
    private String step;
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "exercise_id",referencedColumnName = "id")
    private Exercise exercise;
}
