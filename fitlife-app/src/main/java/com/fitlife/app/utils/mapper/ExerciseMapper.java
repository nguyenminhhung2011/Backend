package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataClass.dto.ExerciseDTO;
import com.fitlife.app.model.exercise.Exercise;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExerciseMapper {
    private final ModelMapper modelMapper;

    public  ExerciseDTO exerciseDTO(Exercise exercise){
        TypeMap<Exercise,ExerciseDTO> typeMap = modelMapper.typeMap(Exercise.class,ExerciseDTO.class);
        return typeMap.map(exercise);
    }
}
