package com.trainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainer.api.OpenAiError;
import com.trainer.api.OpenAiHttpException;
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
public class ResponseBodyCallback implements Callback<ResponseBody> {

    private ObjectMapper objectMapper;

    private final FlowableEmitter<SSE> emitter;
    private final boolean emitDone;

    public ResponseBodyCallback(FlowableEmitter<SSE> emitter, boolean emitDone) {
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
            SSE sse = null;

            while((!emitter.isCancelled()) && (line = reader.readLine()) !=null){
                if (line.startsWith("data: ")){
                    String data = line.substring(5).trim();
                    sse = new SSE(data);
                }else if (line.isEmpty() && sse != null){
                    if (sse.isDone()){
                        if (emitDone){
                            emitter.onNext(sse);
                        }
                        break;
                    }

                    emitter.onNext(sse);
                    sse = null;

                }
                else {
                    throw new SSEFormatException("Invalid see format!" + line);
                }
            }

            emitter.onComplete();

        } catch (OpenAiHttpException | IOException | SSEFormatException e) {
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
