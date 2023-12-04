package com.trainer.api;
import com.trainer.api.assistants.*;
import com.trainer.api.message.Message;
import com.trainer.api.message.MessageRequest;
import io.reactivex.Single;
import retrofit2.http.*;
import java.util.Map;

public interface OpenAiApi {
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
