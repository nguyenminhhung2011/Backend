package com.fitlife.app.config.database.initializer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class DataInitializer {
    private final ExerciseInitializerConfig exerciseInitializerConfig;
    private final NewsHealthInitializerConfig newsHealthInitializerConfig;

    public DataInitializer(ExerciseInitializerConfig exerciseInitializerConfig, NewsHealthInitializerConfig newsHealthInitializerConfig) {
        this.exerciseInitializerConfig = exerciseInitializerConfig;
        this.newsHealthInitializerConfig = newsHealthInitializerConfig;
    }

    @PostConstruct
    public void run() throws Exception {
        exerciseInitializerConfig.run();
        newsHealthInitializerConfig.run();
    }
}
