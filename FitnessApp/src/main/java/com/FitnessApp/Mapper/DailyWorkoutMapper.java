package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DataClass.DailyWorkoutDTO;
import com.FitnessApp.DTO.DataClass.ExerciseDTO;
import com.FitnessApp.Model.DailyWorkout;
import com.FitnessApp.Model.Exercise;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DailyWorkoutMapper {
    private ModelMapper modelMapper;
    private ExerciseMapper exerciseMapper;

    public  DailyWorkoutDTO dailyWorkoutDTO(DailyWorkout dailyWorkout){
        TypeMap<DailyWorkout,DailyWorkoutDTO> propertyMapper =
                modelMapper.typeMap(DailyWorkout.class,DailyWorkoutDTO.class);

        Converter<List<Exercise>, List<ExerciseDTO>> converter = c -> c
                .getSource()
                .stream()
                .map(exercise -> exerciseMapper.exerciseDTO(exercise))
                .toList();

        return propertyMapper
                .addMappings(mapper -> mapper.using(converter).map(DailyWorkout::getSessions,DailyWorkoutDTO::setSessions))
                .map(dailyWorkout);
    }
}
