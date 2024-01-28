package com.fitlife.app.config.database.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fitlife.app.model.newsHealth.NewsHealth;
import com.fitlife.app.repository.jpa.NewsHealthRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
@Configuration
public class NewsHealthInitializerConfig extends DatabaseInitializerConfig {
    @Value(value = "${com.fitlife.database.initial.health_news}")
    private String healthNews;

    private final NewsHealthRepository newsHealthRepository;

    public NewsHealthInitializerConfig(NewsHealthRepository newsHealthRepository) {
        this.newsHealthRepository = newsHealthRepository;
    }
    @Override
    public void run() throws Exception {
        if (isInitialized()) {
            return;
        }
        TypeReference<List<NewsHealth>> typeReferenceNewsHealth = new TypeReference<>() {};
        InputStream newsInputStream = TypeReference.class.getResourceAsStream(healthNews); try {
            List<NewsHealth> newsHealths = objectMapper.readValue(newsInputStream,typeReferenceNewsHealth);

            for(NewsHealth newsHealth: newsHealths) {
                newsHealthRepository.saveAndFlush(newsHealth);
            }

        } catch (Exception e){
            System.out.println("Unable to initialize exercise data: " + e.getMessage());
        }
    }

    @Override
    public boolean isInitialized() {
        return newsHealthRepository.count() > 0;
    }
}
