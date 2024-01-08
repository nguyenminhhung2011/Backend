package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataclass.dto.DailyWorkoutDTO;
import com.fitlife.app.dataclass.dto.ExerciseDTO;
import com.fitlife.app.model.Workout.DailyWorkout;
import com.fitlife.app.model.Exercise.Exercise;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
