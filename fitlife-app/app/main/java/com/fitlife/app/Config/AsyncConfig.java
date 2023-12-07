package com.fitlife.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){
        return new ConcurrentTaskExecutor(Executors.newCachedThreadPool());
    }
}
