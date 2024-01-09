package com.fitlife.app.config.database.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer  implements CommandLineRunner {
    private final ExerciseInitializer exerciseInitializer;
    private final NewsHealthInitializer newsHealthInitializer;

    public DataInitializer(ExerciseInitializer exerciseInitializer, NewsHealthInitializer newsHealthInitializer) {
        this.exerciseInitializer = exerciseInitializer;
        this.newsHealthInitializer = newsHealthInitializer;
    }

    @Override
    public void run(String... args) throws Exception {
        exerciseInitializer.run();
        newsHealthInitializer.run();
    }
}
