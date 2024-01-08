package com.fitlife.app.Utils.Mapper;

import com.fitlife.app.DTO.DataClass.ExerciseDTO;
import com.fitlife.app.Model.Exercise.Exercise;
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
