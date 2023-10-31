package com.FitnessApp.Mapper;

import com.FitnessApp.Model.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExerciseMapperTest {

    @Autowired
    ExerciseMapper exerciseMapper;

    @Test
    void exerciseDTO() {
        exerciseMapper.exerciseDTO(new Exercise(0L,"","","",0,"",new ArrayList<>(),0,0));
    }
}