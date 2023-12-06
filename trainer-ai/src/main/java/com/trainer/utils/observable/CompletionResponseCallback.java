package com.trainer.utils.observable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.models.errors.OpenAiError;
import com.trainer.models.errors.OpenAiHttpException;
import io.reactivex.FlowableEmitter;
import lombok.NonNull;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CompletionResponseCallback implements Callback<ResponseBody> {

    private ObjectMapper objectMapper;

    private final FlowableEmitter<CompletionEvent> emitter;
    private final boolean emitDone;

    public CompletionResponseCallback(FlowableEmitter<CompletionEvent> emitter, boolean emitDone) {
        this.emitter = emitter;
        this.emitDone = emitDone;
    }

    @Autowired
    public void addObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
        BufferedReader reader = null;
        try {
            if (!response.isSuccessful()) {
                HttpException e = new HttpException(response);
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    throw e;
                } else {
                    OpenAiError error = objectMapper.readValue(
                            errorBody.string(),
                            OpenAiError.class
                    );
                    throw new OpenAiHttpException(error, e, e.code());
                }
            }

            if (response.body() == null){
                throw new RuntimeException("Response body is null!");
            }

            InputStream in = response.body().byteStream();
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String line = "";
            CompletionEvent completionEvent = null;

            while((!emitter.isCancelled()) && (line = reader.readLine()) !=null){
                if (line.startsWith("data: ")){
                    String data = line.substring(5).trim();
                    completionEvent = new CompletionEvent(data);
                }else if (line.isEmpty() && completionEvent != null){
                    if (completionEvent.isDone()){
                        if (emitDone){
                            emitter.onNext(completionEvent);
                        }
                        break;
                    }

                    emitter.onNext(completionEvent);
                    completionEvent = null;

                }
                else {
                    throw new CompletionEventFormatException("Invalid see format!" + line);
                }
            }

            emitter.onComplete();

        } catch (OpenAiHttpException | IOException | CompletionEventFormatException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
        emitter.onError(throwable);
    }
}
