package com.fitlife.app.config.database.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fitlife.app.model.newsHealth.NewsHealth;
import com.fitlife.app.repository.jpa.NewsHealthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
@Component
public class NewsHealthInitializer extends DatabaseInitializerConfig {
    @Value(value = "${com.fitlife.database.initial.health_news}")
    private String healthNews;

    private final NewsHealthRepository newsHealthRepository;

    public NewsHealthInitializer(NewsHealthRepository newsHealthRepository) {
        this.newsHealthRepository = newsHealthRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
}
