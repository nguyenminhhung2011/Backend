package com.fitlife.app.Utils.Mapper;

import com.fitlife.app.DTO.DataClass.DailyWorkoutDTO;
import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.WorkoutPlan;
import lombok.AllArgsConstructor;
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
                = modelMapper.typeMap(WorkoutPlan.class,WorkoutPlanDTO.class);

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
