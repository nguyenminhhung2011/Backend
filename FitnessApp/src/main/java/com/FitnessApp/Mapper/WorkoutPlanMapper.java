package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DailyWorkoutDTO;
import com.FitnessApp.DTO.WorkoutPlanDTO;
import com.FitnessApp.Model.DailyWorkout;
import com.FitnessApp.Model.WorkoutPlan;
import lombok.AllArgsConstructor;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmUnsavedValueCompositeIdEnum;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WorkoutPlanMapper {
    private final DailyWorkoutMapper dailyWorkoutMapper;
    private final ModelMapper modelMapper;

    public WorkoutPlanDTO workoutPlanDTO(WorkoutPlan workoutPlan){
        TypeMap<WorkoutPlan,WorkoutPlanDTO> typeMap
                = modelMapper.createTypeMap(WorkoutPlan.class,WorkoutPlanDTO.class);

        Converter<List<DailyWorkout>, List<DailyWorkoutDTO>> dailyWorkoutDTOConverter  = source -> source.getSource()
                .stream()
                .map(dailyWorkoutMapper::dailyWorkoutDTO)
                .toList() ;

        return typeMap
                .addMappings(mapper -> mapper.using(dailyWorkoutDTOConverter)
                .map(WorkoutPlan::getDailyWorkouts,WorkoutPlanDTO::setDailyWorkouts))
                .map(workoutPlan);

    }
}
