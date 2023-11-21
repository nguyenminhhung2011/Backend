package com.FitnessApp.Config;

import com.FitnessApp.Model.Exercise.BodyPart;
import com.FitnessApp.Model.Exercise.Equipment;
import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Model.Exercise.Target;
import com.FitnessApp.Repository.BodyPartRepository;
import com.FitnessApp.Repository.EquipmentRepository;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Repository.TargetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class InitialDatabaseConfig {

    private final ExerciseRepository exerciseRepository;
    private final BodyPartRepository bodyPartRepository;
    private final EquipmentRepository equipmentRepository;
    private final TargetRepository targetRepository;
    private final ObjectMapper objectMapper;

    @Value(value = "${com.FitnessApp.database.initial.exercise}")
    private String exercise;

    @Value(value = "${com.FitnessApp.database.initial.bodypart}")
    private String bodyPart;

    @Value(value = "${com.FitnessApp.database.initial.equipment}")
    private String equipment;

    @Value(value = "${com.FitnessApp.database.initial.target}")
    private String target;

    public InitialDatabaseConfig(ExerciseRepository exerciseRepository, BodyPartRepository bodyPartRepository, EquipmentRepository equipmentRepository, TargetRepository targetRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
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
            exerciseRepository.saveAll(exercises);

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
