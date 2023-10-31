package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.ExerciseDTO;
import com.FitnessApp.Model.Exercise;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExerciseMapper {
    private ModelMapper modelMapper;

    public  ExerciseDTO exerciseDTO(Exercise exercise){
        return modelMapper.map(exercise,ExerciseDTO.class);
    }
}
