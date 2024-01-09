package com.trainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.configs.RetrofitConfig;
import com.trainer.models.OpenAiApi;
import com.trainer.models.errors.OpenAiError;
import com.trainer.models.errors.OpenAiHttpException;
import com.trainer.utils.StreamFlowUtils;
import com.trainer.utils.interceptor.AuthenticationInterceptor;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

//@EnableConfigurationProperties(value = {ObjectMapperConfig.class, OkHttpConfig.class, RetrofitConfig.class})
@Service
@ComponentScan(basePackages = {"com.trainer"})
abstract class OpenAiBaseService {
    protected OpenAiApi api;
    protected ObjectMapper mapper;
    protected OkHttpClient okHttpClient;
    protected RetrofitConfig retrofitConfig;
    protected Retrofit retrofit;
    protected ExecutorService executorService;
    protected StreamFlowUtils streamFlowService;

    @Autowired
    @Qualifier(value = "StreamFlowUtilsTrainer")
    void addStreamFlowService(StreamFlowUtils streamFlowService) {
        this.streamFlowService = streamFlowService;
    }

    @Autowired
    @Qualifier(value = "TrainerMapperConfigTrainer")
    void addMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Autowired
    void addRetrofitConfig(RetrofitConfig retrofitConfig) {
        this.retrofitConfig = retrofitConfig;
    }

    @Autowired
    @Qualifier(value = "OkHttpConfigTrainer")
    void addOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.executorService = this.okHttpClient.dispatcher().executorService();
    }

    @Autowired
    @Qualifier(value = "RetrofitConfigTrainer")
    void addRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
        this.api = retrofit.create(OpenAiApi.class);
    }

    public void updateToken(String token) {
        this.okHttpClient = this.okHttpClient.newBuilder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .build();
        this.executorService = this.okHttpClient.dispatcher().executorService();
        this.retrofit = retrofitConfig.buildRetrofit(this.okHttpClient);
        this.api = retrofit.create(OpenAiApi.class);
    }

    public <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = Objects.requireNonNull(e.response()).errorBody().string();

                OpenAiError error = mapper.readValue(errorBody, OpenAiError.class);
                throw new OpenAiHttpException(error, e, e.code());
            } catch (IOException ex) {
                // couldn't parse OpenAI error
                throw e;
            }
        }
    }
}
