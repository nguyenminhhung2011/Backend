package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DailyWorkoutDTO;
import com.FitnessApp.DTO.ExerciseDTO;
import com.FitnessApp.Model.DailyWorkout;
import com.FitnessApp.Model.Exercise;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DailyWorkoutMapper {
    private ModelMapper modelMapper;
    private ExerciseMapper exerciseMapper;

    public  DailyWorkoutDTO dailyWorkoutDTO(DailyWorkout dailyWorkout){
        TypeMap<DailyWorkout,DailyWorkoutDTO> propertyMapper =
                modelMapper.createTypeMap(DailyWorkout.class,DailyWorkoutDTO.class);

        Converter<Exercise,ExerciseDTO> converter = c -> exerciseMapper.exerciseDTO(c.getSource());

        return propertyMapper
                .addMappings(mapper -> mapper.using(converter)
                .map(DailyWorkout::getExercises,DailyWorkoutDTO::setExerciseDTOList))
                .map(dailyWorkout);

    }
}
