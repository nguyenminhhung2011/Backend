package com.trainer.configs;

import com.trainer.utils.interceptor.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfig {
    @Value(value = "${openai.api.key}")
    private  String token;
    @Value("${openai.api.timeout:}")
    private long timeout;
    @Bean
    public OkHttpClient createOkHttpClient() {
        return  new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .readTimeout(Duration.ofSeconds(timeout).toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

}
