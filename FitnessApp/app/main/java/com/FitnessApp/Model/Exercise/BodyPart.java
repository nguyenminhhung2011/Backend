package com.FitnessApp.Model.Exercise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BodyPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
    private Long id;
    @NotBlank
    private String name;

    public BodyPart(String name) {
        this.name = name;
    }
}
