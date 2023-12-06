package com.trainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.models.OpenAiApi;
import com.trainer.models.errors.OpenAiError;
import com.trainer.models.errors.OpenAiHttpException;
import com.trainer.configs.ObjectMapperConfig;
import com.trainer.configs.OkHttpConfig;
import com.trainer.configs.RetrofitConfig;
import com.trainer.utils.StreamFlowUtils;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@EnableConfigurationProperties(value = {ObjectMapperConfig.class, OkHttpConfig.class, RetrofitConfig.class, StreamFlowUtils.class})
public abstract class OpenAiBaseService {
    protected OpenAiApi api;
    protected ObjectMapper mapper;
    protected OkHttpClient okHttpClient;
    protected Retrofit retrofit;
    protected ExecutorService executorService;
    protected StreamFlowUtils streamFlowService;

    @Autowired
    void addStreamFlowService(StreamFlowUtils streamFlowService) {
        this.streamFlowService = streamFlowService;
    }

    @Autowired
    void addMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Autowired
    void addOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.executorService = this.okHttpClient.dispatcher().executorService();
    }

    @Autowired
    void addRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
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
