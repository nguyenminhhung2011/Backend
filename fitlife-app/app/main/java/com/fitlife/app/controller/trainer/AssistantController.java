package com.fitlife.app.Controller.Trainer;

import com.fitlife.app.DTO.Request.Trainer.AssistantChatRequest;
import com.fitlife.app.DTO.Request.Trainer.ChatDto;
import com.fitlife.app.DTO.Request.Trainer.ChatThreadDto;
import com.fitlife.app.DTO.Request.Trainer.CreateChatThreadRequest;
import com.fitlife.app.Security.Model.CurrentUser;
import com.fitlife.app.Security.Model.FitLifeUserDetail;
import com.fitlife.app.Service.Trainer.Assistant.AssistantService;
import com.fitlife.app.Service.Trainer.Chat.ChatService;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/chat-stream/{threadId}")
    public Flux<ResponseEntity<String>> createChatStream(@RequestBody AssistantChatRequest request, @PathVariable String threadId) {
        return assistantService.generateCompletionStream(UUID.fromString(threadId), request).map(ResponseEntity::ok);
    }

    @PostMapping("/chat-stream")
    public Flux<ResponseEntity<String>> createChatStream(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AssistantChatRequest request) {
        return assistantService.generateCompletionStream(userDetail.getUser(), request).map(ResponseEntity::ok);
    }

    @GetMapping("/chat/{threadId}")
    public Flux<ResponseEntity<ChatDto>> getChatByThreadID(@PathVariable UUID threadId) {
        return chatService.getChatByThread(threadId).map(ResponseEntity::ok);
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
