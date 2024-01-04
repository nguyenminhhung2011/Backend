package com.trainer.configs;

import com.trainer.utils.interceptor.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:application.properties")
class OkHttpConfig {
    @Value(value = "${openai.api.key}")
    private  String token;
    @Value(value = "${openai.api.timeout:10000}")
    private String timeout;
    @Bean(name = "OkHttpConfigTrainer")
    public OkHttpClient createOkHttpClient() {
        return  new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .readTimeout(Duration.ofSeconds(Long.parseLong(timeout)).toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

}
