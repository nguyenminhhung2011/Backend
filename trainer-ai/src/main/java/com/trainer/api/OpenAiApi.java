package com.trainer.api;
import com.trainer.api.assistants.*;
import com.trainer.api.message.Message;
import com.trainer.api.message.MessageRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.http.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.Flow;

public interface OpenAiApi {

    /*
    * Message
    * */
    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/threads/{thread_id}/messages")
    Mono<Message> createMessage(@Path("thread_id") String threadId, @Body MessageRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages/{message_id}")
    Mono<Message> retrieveMessage(@Path("thread_id") String threadId, @Path("message_id") String messageId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/threads/{thread_id}/messages/{message_id}")
    Mono<Message> modifyMessage(@Path("thread_id") String threadId, @Path("message_id") String messageId, @Body Map<String, String> metadata);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages")
    Mono<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/threads/{thread_id}/messages")
    Mono<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId, @QueryMap Map<String, Object> filterRequest);


    /*
    Assistant
     */

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants")
    Mono<Assistant> createAssistant(@Body AssistantRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}")
    Mono<Assistant> retrieveAssistant(@Path("assistant_id") String assistantId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants/{assistant_id}")
    Mono<Assistant> modifyAssistant(@Path("assistant_id") String assistantId, @Body ModifyAssistantRequest request);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @DELETE("/v1/assistants/{assistant_id}")
    Mono<DeleteResult> deleteAssistant(@Path("assistant_id") String assistantId);


    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants")
    Mono<OpenAiResponse<Assistant>> listAssistants(@QueryMap Map<String, Object> filterRequest);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @POST("/v1/assistants/{assistant_id}/files")
    Mono<AssistantFile> createAssistantFile(@Path("assistant_id") String assistantId, @Body AssistantFileRequest fileRequest);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}/files/{file_id}")
    Mono<AssistantFile> retrieveAssistantFile(@Path("assistant_id") String assistantId, @Path("file_id") String fileId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @DELETE("/v1/assistants/{assistant_id}/files/{file_id}")
    Mono<DeleteResult> deleteAssistantFile(@Path("assistant_id") String assistantId, @Path("file_id") String fileId);

    @Headers({"OpenAI-Beta: assistants=v1"})
    @GET("/v1/assistants/{assistant_id}/files")
    Mono<OpenAiResponse<Assistant>> listAssistantFiles(@Path("assistant_id") String assistantId, @QueryMap Map<String, Object> filterRequest);


}
