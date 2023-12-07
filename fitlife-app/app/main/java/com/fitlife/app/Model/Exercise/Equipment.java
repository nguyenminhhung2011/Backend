package com.fitlife.app.Model.Exercise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
