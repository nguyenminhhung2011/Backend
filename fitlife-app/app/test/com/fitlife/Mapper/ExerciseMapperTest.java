package com.fitlife.Mapper;

import com.fitlife.app.utils.mapper.ExerciseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExerciseMapperTest {

    @Autowired
    ExerciseMapper exerciseMapper;

    @Test
    void exerciseDTO() {
//     final ExerciseDTO exerciseDTO = exerciseMapper.exerciseDTO(new Exercise(0L,"","","",0,"",new ArrayList<>(),0,0));
    }
}