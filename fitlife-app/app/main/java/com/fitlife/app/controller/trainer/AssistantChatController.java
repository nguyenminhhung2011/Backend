package com.fitlife.app.controller.trainer;

import com.fitlife.app.dataClass.request.trainer.AssistantChatRequest;
import com.fitlife.app.dataClass.dto.trainer.ChatDto;
import com.fitlife.app.dataClass.response.trainer.AssistantChatStreamResponse;
import com.fitlife.app.security.model.CurrentUser;
import com.fitlife.app.security.model.FitLifeUserDetail;
import com.fitlife.app.service.trainer.assistant.AssistantChatService;
import com.fitlife.app.service.trainer.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/assistant")
public class AssistantChatController {
    private final AssistantChatService assistantChatService;
    private final ChatService chatService;

    @PostMapping("/chat")
    public Mono<ResponseEntity<?>> createChat(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AssistantChatRequest request) {
        return assistantChatService.generateCompletion(userDetail.getUser(), request).map(ResponseEntity::ok);
    }

    @PostMapping("/chat/{threadId}")
    public Mono<ResponseEntity<?>> createChat(@RequestBody AssistantChatRequest request, @PathVariable String threadId) {
        return assistantChatService.generateCompletion(UUID.fromString(threadId), request).map(ResponseEntity::ok);
    }

    @PostMapping(value = "/chat-stream/{threadId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AssistantChatStreamResponse> createChatStream(@RequestBody AssistantChatRequest request, @PathVariable String threadId) {
        return assistantChatService.generateCompletionStream(UUID.fromString(threadId), request);
    }

    @PostMapping(value ="/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AssistantChatStreamResponse> createChatStream(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AssistantChatRequest request) {
        return assistantChatService.generateCompletionStream(userDetail.getUser(), request);
    }

    @GetMapping("/chat/{threadId}")
    public ResponseEntity<Flux<ChatDto>> getChatByThreadID(@PathVariable UUID threadId) {
        return ResponseEntity.ok(chatService.getChatByThread(threadId));
    }
}
