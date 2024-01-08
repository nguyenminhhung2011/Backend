package com.fitlife.app.controller.trainer;

import com.fitlife.app.dataClass.request.trainer.AssistantChatRequest;
import com.fitlife.app.dataClass.request.trainer.ChatDto;
import com.fitlife.app.dataClass.request.trainer.ChatThreadDto;
import com.fitlife.app.dataClass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.dataClass.response.trainer.AssistantChatStreamResponse;
import com.fitlife.app.security.model.CurrentUser;
import com.fitlife.app.security.model.FitLifeUserDetail;
import com.fitlife.app.service.trainer.Assistant.AssistantService;
import com.fitlife.app.service.trainer.Chat.ChatService;
import com.fitlife.app.service.trainer.Thread.ChatThreadService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
public class AssistantController {
    private final AssistantService assistantService;
    private final ChatThreadService chatThreadService;
    private final ChatService chatService;

    //Chat
    @PostMapping("/chat")
    public Mono<ResponseEntity<?>> createChat(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AssistantChatRequest request) {
        return assistantService.generateCompletion(userDetail.getUser(), request).map(ResponseEntity::ok);
    }

    @PostMapping("/chat/{threadId}")
    public Mono<ResponseEntity<?>> createChat(@RequestBody AssistantChatRequest request, @PathVariable String threadId) {
        return assistantService.generateCompletion(UUID.fromString(threadId), request).map(ResponseEntity::ok);
    }

    @PostMapping(value = "/chat-stream/{threadId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AssistantChatStreamResponse> createChatStream(@RequestBody AssistantChatRequest request, @PathVariable String threadId) {
        return assistantService.generateCompletionStream(UUID.fromString(threadId), request);
    }

    @PostMapping(value ="/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AssistantChatStreamResponse> createChatStream(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AssistantChatRequest request) {
        return assistantService.generateCompletionStream(userDetail.getUser(), request);
    }

    @GetMapping("/chat/{threadId}")
    public ResponseEntity<Flux<ChatDto>> getChatByThreadID(@PathVariable UUID threadId) {
        return ResponseEntity.ok(chatService.getChatByThread(threadId));
    }

    //Thread
    @GetMapping("/thread")
    public Flux<ChatThreadDto> getThreadByUser(@CurrentUser FitLifeUserDetail userDetail) {
        return chatThreadService.getThreadsByUserId(userDetail.getId());
    }

    @GetMapping("/thread/{threadId}")
    public Mono<ResponseEntity<ChatThreadDto>> getThreadByUser(@PathVariable UUID threadId) {
        return chatThreadService.getThreadById(threadId).map(ResponseEntity::ok);
    }

    @DeleteMapping("/thread/{threadId}")
    public Mono<ResponseEntity<?>> deleteThread(@PathVariable UUID threadId) {
        return chatThreadService.deleteThread(threadId).then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/thread")
    public ResponseEntity<ChatThreadDto> createThread(@RequestBody CreateChatThreadRequest request) {
        return ResponseEntity.ok(chatThreadService.createChatThread(request));
    }
}
