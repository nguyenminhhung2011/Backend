package com.fitlife.app.Config;

import com.fitlife.app.Model.Exercise.*;
import com.fitlife.app.Repository.Exercise.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class InitialDatabaseConfig {

    private final ExerciseRepository exerciseRepository;
    private final StepRepository stepRepository ;
    private final BodyPartRepository bodyPartRepository;
    private final EquipmentRepository equipmentRepository;
    private final TargetRepository targetRepository;
    private final ObjectMapper objectMapper;

    @Value(value = "${com.fitlife.database.initial.exercise}")
    private String exercise;

    @Value(value = "${com.fitlife.database.initial.bodypart}")
    private String bodyPart;

    @Value(value = "${com.fitlife.database.initial.equipment}")
    private String equipment;

    @Value(value = "${com.fitlife.database.initial.target}")
    private String target;

    public InitialDatabaseConfig(ExerciseRepository exerciseRepository, StepRepository stepRepository, BodyPartRepository bodyPartRepository, EquipmentRepository equipmentRepository, TargetRepository targetRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
        this.stepRepository = stepRepository;
        this.bodyPartRepository = bodyPartRepository;
        this.equipmentRepository = equipmentRepository;
        this.targetRepository = targetRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void initializeDatabase(){
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

    //Create instance of a class base on Class<T> type
    private <T> T createInstanceFromClass(Class<T> type,String value){
        try {
            return type.getDeclaredConstructor(String.class).newInstance(value);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //Create List Data<T> using reflection
    <T> List<T> initializeListData(Class<T> type,String path){
        try {
            AtomicReference<List<T>> item = new AtomicReference<>(new ArrayList<>());
            InputStream inputStream = TypeReference.class.getResourceAsStream(path);
            objectMapper.readTree(inputStream).forEach(
                    jsonNode -> item.getAndUpdate(listItem -> {
                        final List<T> newList = new ArrayList<>(List.copyOf(listItem));

                        newList.add(createInstanceFromClass(type,jsonNode.asText()));

                        return newList;
                    })
            );

            return item.get();
        } catch (Exception e){
            throw new RuntimeException("Unable to initialize exercise data: " + e.getMessage());
        }
    }
}
