package com.FitnessApp.Mapper;

import com.FitnessApp.Model.WorkoutPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkoutPlanMapperTest {
    @Autowired
    WorkoutPlanMapper workoutPlanMapper;

    @Test
    void setWorkoutPlanMapper() {
        workoutPlanMapper.workoutPlanDTO(WorkoutPlan.builder().dailyWorkouts(new ArrayList<>()).build());
    }
}