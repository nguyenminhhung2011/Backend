package com.FitnessApp.Config;

import com.FitnessApp.Model.Exercise.Steps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ObjectMapperTest {
    @Autowired
    ObjectMapper objectMapper ;
    String testJson ;

    @BeforeEach
    void setUp() {
        testJson = "{\"bodyPart\":\"waist\",\"equipment\":\"body weight\",\"gifUrl\":\"https://v2.exercisedb.io/image/4yClX6A1UHeNIh\",\"id\":\"0001\",\"name\":\"3/4 sit-up\",\"target\":\"abs\",\"secondaryMuscles\":[\"hip flexors\",\"lower back\"],\"instructions\":[\"Lie flat on your back with your knees bent and feet flat on the ground.\",\"Place your hands behind your head with your elbows pointing outwards.\",\"Engaging your abs, slowly lift your upper body off the ground, curling forward until your torso is at a 45-degree angle.\",\"Pause for a moment at the top, then slowly lower your upper body back down to the starting position.\",\"Repeat for the desired number of repetitions.\"]}";
    }

    @Test
    void testMappingJson() {
        try {
            List<Steps> readValue = objectMapper.readValue(testJson, List.class);
            System.out.println(readValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}