package com.fitlife.app.config;

import com.cloudinary.Cloudinary;
import com.trainer.utils.interceptor.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpFitLifeConfig {
    @Value(value = "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")
    private  String token;

    @Value(value = "${openai.api.timeout:10000}")
    private String timeout;

    @Primary
    @Bean(name = "OkHttpConfigTrainer")
    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .readTimeout(Duration.ofSeconds(Long.parseLong(timeout)).toSeconds(), TimeUnit.SECONDS)
                .build();
    }
}
