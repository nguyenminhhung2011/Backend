package com.fitlife.app.config.database.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fitlife.app.model.exercise.*;
import com.fitlife.app.repository.jpa.exercise.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
@Component
public class ExerciseInitializerConfig extends DatabaseInitializerConfig{
    @Value(value = "${com.fitlife.database.initial.exercise}")
    private String exercise;

    @Value(value = "${com.fitlife.database.initial.bodypart}")
    private String bodyPart;

    @Value(value = "${com.fitlife.database.initial.equipment}")
    private String equipment;

    @Value(value = "${com.fitlife.database.initial.target}")
    private String target;
    private final ExerciseRepository exerciseRepository;
    private final StepRepository stepRepository ;
    private final BodyPartRepository bodyPartRepository;
    private final EquipmentRepository equipmentRepository;
    private final TargetRepository targetRepository;

    public ExerciseInitializerConfig(ExerciseRepository exerciseRepository, StepRepository stepRepository, BodyPartRepository bodyPartRepository, EquipmentRepository equipmentRepository, TargetRepository targetRepository) {
        this.exerciseRepository = exerciseRepository;
        this.stepRepository = stepRepository;
        this.bodyPartRepository = bodyPartRepository;
        this.equipmentRepository = equipmentRepository;
        this.targetRepository = targetRepository;
    }

    public void run() throws Exception {
        TypeReference<List<Exercise>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(exercise);
        try {
            List<Exercise> exercises = objectMapper.readValue(inputStream,typeReference);


            for (Exercise exercise : exercises) {
                final var savedExercise = exerciseRepository.saveAndFlush(exercise);
                for (Steps step : exercise.getSteps()) {
                    step.setExercise(savedExercise);
                }
                stepRepository.saveAllAndFlush(exercise.getSteps());
            }
            targetRepository.saveAll(initializeListData(Target.class,target));
            equipmentRepository.saveAll(initializeListData(Equipment.class,equipment));
            bodyPartRepository.saveAll(initializeListData(BodyPart.class,bodyPart));

        } catch (Exception e){
            System.out.println("Unable to initialize exercise data: " + e.getMessage());
        }
    }

}
