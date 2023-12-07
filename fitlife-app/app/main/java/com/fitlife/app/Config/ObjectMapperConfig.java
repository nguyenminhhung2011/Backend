package com.fitlife.app.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(){

//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(List.class, new InstructionsDeserialize());
//
//        objectMapper.registerModule(module);

        return new ObjectMapper();
    }
}
