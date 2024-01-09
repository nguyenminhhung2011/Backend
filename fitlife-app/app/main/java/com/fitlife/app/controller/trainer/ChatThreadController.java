package com.fitlife.app.controller.trainer;

import com.fitlife.app.dataClass.dto.trainer.ChatThreadDetailDto;
import com.fitlife.app.dataClass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.security.model.CurrentUser;
import com.fitlife.app.security.model.FitLifeUserDetail;
import com.fitlife.app.service.trainer.thread.ChatThreadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/thread-chat")
public class ChatThreadController {
    private final ChatThreadService chatThreadService;
    @GetMapping
    public Flux<ChatThreadDetailDto> getThreadByUser(@CurrentUser FitLifeUserDetail userDetail) {
        return chatThreadService.getThreadsByUserId(userDetail.getId());
    }

    @GetMapping("/{threadId}")
    public Mono<ResponseEntity<ChatThreadDetailDto>> getThreadByUser(@PathVariable UUID threadId) {
        return chatThreadService.getThreadById(threadId).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{threadId}")
    public Mono<ResponseEntity<?>> deleteThread(@PathVariable UUID threadId) {
        return chatThreadService.deleteThread(threadId).then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping
    public ResponseEntity<ChatThreadDetailDto> createThread(@CurrentUser FitLifeUserDetail userDetail,@RequestBody CreateChatThreadRequest request) {
        return ResponseEntity.ok(chatThreadService.createChatThread(userDetail.getUser(),request));
    }
}
