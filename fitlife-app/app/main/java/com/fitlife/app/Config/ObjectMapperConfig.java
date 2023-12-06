package com.fitlife.app.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
