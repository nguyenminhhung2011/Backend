package com.trainer.models;

import com.trainer.models.api.assistants.*;
import com.trainer.models.api.completion.CompletionRequest;
import com.trainer.models.api.completion.CompletionResult;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatCompletionResult;
import com.trainer.models.api.embedding.Embedding;
import com.trainer.models.api.embedding.EmbeddingRequest;
import com.trainer.models.api.embedding.EmbeddingResult;
import com.trainer.models.api.file.File;
import com.trainer.models.api.message.Message;
import com.trainer.models.api.message.MessageRequest;
import com.trainer.models.api.threads.Thread;
import com.trainer.models.api.threads.ThreadRequest;
import com.trainer.models.common.DeleteResult;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

@Service
public interface OpenAiApi {
    @Multipart
    @POST("/v1/files")
    Single<File> uploadFile(@Part("purpose") RequestBody purpose, @Part MultipartBody.Part file);

    /*
     * Message
     * */
    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/threads/{thread_id}/messages")
    Single<Message> createMessage(@Path("thread_id") String threadId, @Body MessageRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages/{message_id}")
    Single<Message> retrieveMessage(@Path("thread_id") String threadId, @Path("message_id") String messageId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/threads/{thread_id}/messages/{message_id}")
    Single<Message> modifyMessage(@Path("thread_id") String threadId, @Path("message_id") String messageId, @Body Map<String, String> metadata);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages")
    Single<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages")
    Single<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId, @QueryMap Map<String, Object> filterRequest);

    /*
    Completion and Chat Completion
     */

    @POST("/v1/completions")
    Single<CompletionResult> createCompletion(@Body CompletionRequest request);

    @Streaming
    @POST("/v1/completions")
    Call<ResponseBody> createCompletionStream(@Body CompletionRequest request);

    @POST("/v1/chat/completions")
    Single<ChatCompletionResult> createChatCompletion(@Body ChatCompletionRequest request);

    @Streaming
    @POST("/v1/chat/completions")
    Call<ResponseBody> createChatCompletionStream(@Body ChatCompletionRequest request);

    //    Embedding
    @POST("/v1/embeddings")
    Single<EmbeddingResult> createEmbeddings(@Body EmbeddingRequest request);

    /*
    Thread
     */

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/threads")
    Single<Thread> createThread(@Body ThreadRequest request);

    /*
    Assistant
     */
    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants")
    Single<Assistant> createAssistant(@Body AssistantRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}")
    Single<Assistant> retrieveAssistant(@Path("assistant_id") String assistantId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants/{assistant_id}")
    Single<Assistant> modifyAssistant(@Path("assistant_id") String assistantId, @Body ModifyAssistantRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @DELETE("/v1/assistants/{assistant_id}")
    Single<DeleteResult> deleteAssistant(@Path("assistant_id") String assistantId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants")
    Single<OpenAiResponse<Assistant>> listAssistants(@QueryMap Map<String, Object> filterRequest);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants/{assistant_id}/files")
    Single<AssistantFile> createAssistantFile(@Path("assistant_id") String assistantId, @Body AssistantFileRequest fileRequest);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}/files/{file_id}")
    Single<AssistantFile> retrieveAssistantFile(@Path("assistant_id") String assistantId, @Path("file_id") String fileId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @DELETE("/v1/assistants/{assistant_id}/files/{file_id}")
    Single<DeleteResult> deleteAssistantFile(@Path("assistant_id") String assistantId, @Path("file_id") String fileId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}/files")
    Single<OpenAiResponse<Assistant>> listAssistantFiles(@Path("assistant_id") String assistantId, @QueryMap Map<String, Object> filterRequest);
}
