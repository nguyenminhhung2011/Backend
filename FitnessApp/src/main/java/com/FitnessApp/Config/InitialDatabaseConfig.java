package com.FitnessApp.Config;

import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Model.Steps;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Repository.StepRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class InitialDatabaseConfig {

    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper;

    @Value(value = "${com.FitnessApp.database.initial.exercise}")
    private String exercise;

    @Value(value = "${com.FitnessApp.database.initial.bodypart}")
    private String bodyPart;

    @Value(value = "${com.FitnessApp.database.initial.equipment}")
    private String equipment;

    @Value(value = "${com.FitnessApp.database.initial.targetList}")
    private String targetList;

    public InitialDatabaseConfig(ExerciseRepository exerciseRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void initializeDatabase(){
        TypeReference<List<Exercise>> typeReference = new TypeReference<>() {};

        InputStream inputStream = TypeReference.class.getResourceAsStream(exercise);

        try {
            List<Exercise> exercises = objectMapper.readValue(inputStream,typeReference);
            exerciseRepository.saveAll(exercises);
        } catch (Exception e){
            System.out.println("Unable to initialize exercise data: " + e.getMessage());
        }
    }
}
