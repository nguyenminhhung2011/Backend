package com.FitnessApp.Config;

import com.FitnessApp.DTO.InstructionsDeserialize;
import com.FitnessApp.Model.Steps;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(){

//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(List.class, new InstructionsDeserialize());
//
//        objectMapper.registerModule(module);

        return new ObjectMapper();
    }
}
