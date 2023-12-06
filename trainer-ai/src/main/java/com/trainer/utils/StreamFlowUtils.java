package com.trainer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.utils.observable.CompletionResponseCallback;
import com.trainer.utils.observable.CompletionEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import retrofit2.Call;

@ConfigurationProperties
public class StreamFlowUtils {
    @Autowired
    private ObjectMapper mapper;

    //////////////////////////////////////////////
    /**
     * Calls the Open AI api and returns a Flowable of SSE for streaming
     * omitting the last message.
     *
     * @param apiCall The api call
     */
    public Flowable<CompletionEvent> stream(Call<ResponseBody> apiCall) {
        return stream(apiCall, false);
    }

    /**
     * Calls the Open AI api and returns a Flowable of SSE for streaming.
     *
     * @param apiCall  The api call
     * @param emitDone If true the last message ([DONE]) is emitted
     */
    public  Flowable<CompletionEvent> stream(Call<ResponseBody> apiCall, boolean emitDone) {
        return Flowable.create(emitter -> apiCall.enqueue(new CompletionResponseCallback(emitter, emitDone)), BackpressureStrategy.BUFFER);
    }

    /**
     * Calls the Open AI api and returns a Flowable of type T for streaming
     * omitting the last message.
     *
     * @param apiCall The api call
     * @param cl      Class of type T to return
     */
    public  <T> Flowable<T> stream(Call<ResponseBody> apiCall, Class<T> cl) {
        return stream(apiCall).map(completionEvent -> mapper.readValue(completionEvent.data(), cl));
    }
}
