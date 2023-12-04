package com.trainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Service
public class OpenAiService {
    @Value("${openai.api.url}")
    private  String BASE_URL;

    @Value("${openai.api.timeout:}")
    private long timeout;
    private Duration DEFAULT_TIMEOUT = Duration.ofSeconds(timeout);
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;
    private final ExecutorService executorService;

    public OpenAiService(ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.objectMapper = objectMapper;
        this.okHttpClient = okHttpClient;
        this.executorService = okHttpClient.dispatcher().executorService();
    }

    public void deleteFile(String fileId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteModel(String modelId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteAnswer(String answerId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteSearch(String searchId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteClassifier(String classifierId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTune(String fineTuneId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneEvent(String fineTuneEventId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFile(String fineTuneFileId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneModel(String fineTuneModelId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneAnswer(String fineTuneAnswerId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneSearch(String fineTuneSearchId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneClassifier(String fineTuneClassifierId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTune(String fineTuneFineTuneId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTuneEvent(String fineTuneFineTuneEventId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTuneFile(String fineTuneFineTuneFileId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTuneModel(String fineTuneFineTuneModelId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTuneAnswer(String fineTuneFineTuneAnswerId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteFineTuneFineTuneSearch(String fineTuneFineTuneSearchId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
