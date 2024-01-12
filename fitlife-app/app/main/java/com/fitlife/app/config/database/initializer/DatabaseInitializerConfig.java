package com.fitlife.app.config.database.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
abstract public class DatabaseInitializerConfig  {

    protected ObjectMapper objectMapper;

    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    abstract public void run() throws Exception;

    abstract public boolean isInitialized() throws Exception;

    //Create instance of a class base on Class<T> type
    protected  <T> T createInstanceFromClass(Class<T> type,String value){
        try {
            return type.getDeclaredConstructor(String.class).newInstance(value);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //Create List Data<T> using reflection
    <T> List<T> initializeListData(Class<T> type,String path){
        try {
            AtomicReference<List<T>> item = new AtomicReference<>(new ArrayList<>());
            InputStream inputStream = TypeReference.class.getResourceAsStream(path);
            objectMapper.readTree(inputStream).forEach(
                    jsonNode -> item.getAndUpdate(listItem -> {
                        final List<T> newList = new ArrayList<>(List.copyOf(listItem));

                        newList.add(createInstanceFromClass(type,jsonNode.asText()));

                        return newList;
                    })
            );

            return item.get();
        } catch (Exception e){
            throw new RuntimeException("Unable to initialize exercise data: " + e.getMessage());
        }
    }
}
