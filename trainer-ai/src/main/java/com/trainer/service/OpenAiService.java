package com.trainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.api.OpenAiApi;
import com.trainer.api.OpenAiError;
import com.trainer.api.assistants.Assistant;
import com.trainer.api.completion.CompletionChunk;
import com.trainer.api.completion.chat.ChatCompletionChunk;
import com.trainer.api.OpenAiHttpException;
import com.trainer.api.completion.CompletionRequest;
import com.trainer.api.completion.CompletionResult;
import com.trainer.api.completion.chat.ChatCompletionResult;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.HttpException;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Service
public class OpenAiService {

    private final OpenAiApi api;

    @Value("${openai.api.url}")
    private  String BASE_URL;

    @Value("${openai.api.timeout:}")
    private long timeout;
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(timeout);
    private final ObjectMapper mapper;
    private final OkHttpClient okHttpClient;
    private final ExecutorService executorService;

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

    public OpenAiService(OpenAiApi api, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.api = api;
        this.mapper = objectMapper;
        this.okHttpClient = okHttpClient;
        this.executorService = okHttpClient.dispatcher().executorService();
    }

    //Chat Completion && Completion

    public CompletionResult createCompletion(CompletionRequest request) {
        return execute(api.createCompletion(request));
    }

    public Flowable<CompletionChunk> streamCompletion(CompletionRequest request) {
        request.setStream(true);

        return stream(api.createCompletionStream(request), CompletionChunk.class);
    }

    public ChatCompletionResult createChatCompletion(CompletionRequest request) {
        return execute(api.createChatCompletion(request));
    }

    public Flowable<ChatCompletionChunk> streamChatCompletion(CompletionRequest request) {
        request.setStream(true);

        return stream(api.createChatCompletionStream(request), ChatCompletionChunk.class);
    }



    /*
        Assistant
     */




    /**
     * Calls the Open AI api and returns a Flowable of SSE for streaming
     * omitting the last message.
     *
     * @param apiCall The api call
     */
    public  Flowable<SSE> stream(Call<ResponseBody> apiCall) {
        return stream(apiCall, false);
    }

    /**
     * Calls the Open AI api and returns a Flowable of SSE for streaming.
     *
     * @param apiCall  The api call
     * @param emitDone If true the last message ([DONE]) is emitted
     */
    public  Flowable<SSE> stream(Call<ResponseBody> apiCall, boolean emitDone) {
        return Flowable.create(emitter -> apiCall.enqueue(new ResponseBodyCallback(emitter, emitDone)), BackpressureStrategy.BUFFER);
    }

    /**
     * Calls the Open AI api and returns a Flowable of type T for streaming
     * omitting the last message.
     *
     * @param apiCall The api call
     * @param cl      Class of type T to return
     */
    public  <T> Flowable<T> stream(Call<ResponseBody> apiCall, Class<T> cl) {
        return stream(apiCall).map(sse -> mapper.readValue(sse.getData(), cl));
    }
}
