package com.fitlife.app.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FetchExercisesDBTest {
    @Autowired
    FetchExercisesDB fetchExercisesDB;

    @Test
    void test(){
        try {
            fetchExercisesDB.fetch();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}