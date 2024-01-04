package com.trainer.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@PropertySource("classpath:application.properties")
class RetrofitConfig {
    @Value("${openai.api.url}")
    private String BASE_URL;

    protected ObjectMapper mapper;
    protected OkHttpClient okHttpClient;

    @Autowired
    @Qualifier(value = "TrainerMapperConfigTrainer")
    void addMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Autowired
    @Qualifier(value = "OkHttpConfigTrainer")
    void addOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Bean(name = "RetrofitConfigTrainer")
    public Retrofit defaultRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
