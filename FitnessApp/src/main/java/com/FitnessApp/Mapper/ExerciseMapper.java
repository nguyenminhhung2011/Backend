package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.ExerciseDTO;
import com.FitnessApp.Model.Exercise;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@AllArgsConstructor
public class ExerciseMapper {
    private final ModelMapper modelMapper;

    public  ExerciseDTO exerciseDTO(Exercise exercise){
        TypeMap<Exercise,ExerciseDTO> typeMap = modelMapper.typeMap(Exercise.class,ExerciseDTO.class);
        return typeMap.map(exercise);
    }
}
