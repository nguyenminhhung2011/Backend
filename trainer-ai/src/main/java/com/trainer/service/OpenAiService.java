package com.trainer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.trainer.models.*;
import com.trainer.models.api.assistants.*;
import com.trainer.models.api.completion.CompletionChunk;
import com.trainer.models.api.completion.chat.ChatCompletionChunk;
import com.trainer.models.api.completion.CompletionRequest;
import com.trainer.models.api.completion.CompletionResult;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatCompletionResult;
import com.trainer.models.api.embedding.EmbeddingRequest;
import com.trainer.models.api.embedding.EmbeddingResult;
import com.trainer.models.api.file.File;
import com.trainer.models.api.threads.Thread;
import com.trainer.models.api.threads.ThreadRequest;
import com.trainer.models.common.DeleteResult;
import com.trainer.models.common.ListSearchParameters;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.context.annotation.  ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ComponentScan(basePackages = {"com.trainer"})
public class OpenAiService extends OpenAiBaseService {
    //
    // Chat Completion && Completion
    public CompletionResult createCompletion(CompletionRequest request) {
        return execute(api.createCompletion(request));
    }

    public Flowable<CompletionChunk> streamCompletion(CompletionRequest request) {
        request.setStream(true);
        return streamFlowService.stream(api.createCompletionStream(request), CompletionChunk.class);
    }

    public ChatCompletionResult createChatCompletion(ChatCompletionRequest request) {
        return execute(api.createChatCompletion(request));
    }

    public Flowable<ChatCompletionChunk> streamChatCompletion(ChatCompletionRequest request) {
        request.setStream(true);
        return streamFlowService.stream(api.createChatCompletionStream(request), ChatCompletionChunk.class);
    }

    /*
        Assistant
     */
    public Assistant createAssistant(AssistantRequest request) {
        return execute(api.createAssistant(request));
    }

    public OpenAiResponse<Assistant> listAssistants(ListSearchParameters params) {
        Map<String, Object> queryParameters = mapper.convertValue(params, new TypeReference<>() {
        });
        return execute(api.listAssistants(queryParameters));
    }

    public File uploadFile(String purpose, String filepath) {
        java.io.File file = new java.io.File(filepath);
        RequestBody purposeBody = RequestBody.create(purpose,MultipartBody.FORM);
        RequestBody fileBody = RequestBody.create(file,MediaType.parse("text"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", filepath, fileBody);

        return execute(api.uploadFile(purposeBody, body));
    }

    //Embedding
    public EmbeddingResult createEmbeddings(EmbeddingRequest request) {
        return execute(api.createEmbeddings(request));
    }



    public Assistant retrieveAssistant(String assistantId) {
        return execute(api.retrieveAssistant(assistantId));
    }

    public Assistant modifyAssistant(String assistantId, ModifyAssistantRequest request) {
        return execute(api.modifyAssistant(assistantId, request));
    }

    public DeleteResult deleteAssistant(String assistantId) {
        return execute(api.deleteAssistant(assistantId));
    }

    public OpenAiResponse<Assistant> listAssistant(ListSearchParameters params) {
        Map<String, Object> queryParameters = mapper.convertValue(params, new TypeReference<>() {
        });
        return execute(api.listAssistants(queryParameters));
    }

    public AssistantFile createAssistantFile(String assistantId, AssistantFileRequest fileRequest) {
        return execute(api.createAssistantFile(assistantId, fileRequest));
    }

    public AssistantFile retrieveAssistantFile(String assistantId, String fileId) {
        return execute(api.retrieveAssistantFile(assistantId, fileId));
    }

    public DeleteResult deleteAssistantFile(String assistantId, String fileId) {
        return execute(api.deleteAssistantFile(assistantId, fileId));
    }

    public OpenAiResponse<Assistant> listAssistantFiles(String assistantId, ListSearchParameters params) {
        Map<String, Object> queryParameters = mapper.convertValue(params, new TypeReference<Map<String, Object>>() {
        });
        return execute(api.listAssistantFiles(assistantId, queryParameters));
    }

    /*
        Thread
     */
    public Thread createThread(ThreadRequest request) {
        return execute(api.createThread(request));
    }
}
