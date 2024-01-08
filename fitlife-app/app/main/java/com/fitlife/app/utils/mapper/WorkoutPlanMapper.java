package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataclass.dto.DailyWorkoutDTO;
import com.fitlife.app.dataclass.dto.WorkoutPlanDTO;
import com.fitlife.app.model.Workout.DailyWorkout;
import com.fitlife.app.model.Workout.WorkoutPlan;
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
