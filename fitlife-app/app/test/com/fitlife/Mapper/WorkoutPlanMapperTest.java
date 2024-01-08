package com.fitlife.Mapper;

import com.fitlife.app.model.workout.WorkoutPlan;
import com.fitlife.app.utils.mapper.WorkoutPlanMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class WorkoutPlanMapperTest {
    @Autowired
    WorkoutPlanMapper workoutPlanMapper;

    @Test
    void setWorkoutPlanMapper() {
        workoutPlanMapper.workoutPlanDTO(WorkoutPlan.builder().dailyWorkouts(new ArrayList<>()).build());
    }
}