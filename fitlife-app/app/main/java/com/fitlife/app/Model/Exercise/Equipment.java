package com.fitlife.app.Model.Exercise;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitlife.app.DTO.Views.ExerciseViews;
import com.fitlife.app.Model.session.Session;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
    private Long id;
    @NotBlank
    private String name;
    public Equipment(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Equipment";
    }
}
