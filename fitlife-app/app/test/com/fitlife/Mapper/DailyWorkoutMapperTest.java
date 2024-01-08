package com.fitlife.Mapper;

import com.fitlife.app.utils.mapper.DailyWorkoutMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DailyWorkoutMapperTest {
    @Autowired
    DailyWorkoutMapper dailyWorkoutMapper;

    @Test
    void testDailyWorkoutMapper() {
//     final DailyWorkoutDTO dailyWorkoutDTO = dailyWorkoutMapper.dailyWorkoutDTO(
//                DailyWorkout.builder()
//                .workoutDuration(1)
//                .id(0L)
//                        .exercises(List.of(new Exercise(0L,"","","",0,"",new ArrayList<>(),0,0)))
//                .name("Hoang")
//                .description("Hoang")
//                .breakTime(1)
//                        .time(0L)
//                .build());
//        System.out.println();
    }
}