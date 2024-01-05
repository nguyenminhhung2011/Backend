package com.fitlife.app.Controller.Trainer;

import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.Repository.Trainer.ChatThreadJpaRepository;
import com.fitlife.app.Repository.User.UserRepository;
import com.fitlife.app.Security.Model.CurrentUser;
import com.fitlife.app.Security.Model.FitLifeUserDetail;
import com.fitlife.app.Service.Trainer.Assistant.AssistantService;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final AssistantService assistantService;
    private ChatThreadService chatThreadService;
    UserRepository userRepository;
    ChatThreadJpaRepository chatThreadJpaRepository;
    ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    @PostMapping("/add")
    public void addTrainer() {
        User user = User.builder()
                .username("hoang")
                .password("123456")
                .build();
        user = userRepository.save(user);
        chatThreadJpaRepository.save(ChatThread.builder()
                .title("Testing")
                .user(user)
                .build());
    }

    @GetMapping(value = "/get",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatThread>> get() {
        return chatThreadR2dbcRepository.findAll()
                .map(chatThread -> ServerSentEvent.<ChatThread>builder()
                        .id(chatThread.getId().toString())
                        .data(chatThread)
                        .build());
    }

    @GetMapping(value = "/get")
    public Flux<ChatThread> getReactive() {
        return chatThreadR2dbcRepository.findAll();
    }

    @GetMapping("/chat")
    public Mono<String> getTrainer(@CurrentUser FitLifeUserDetail flUserDetail, @RequestBody ChatCompletionRequest message) {
        return assistantService.generateCompletion(flUserDetail.getUser(), message);
    }

//
//    @PostMapping("/delete/{id}")
//    public ResponseEntity<?> deleteTrainer( @PathVariable String id) {
//        return null;
//    }
//
//    @PostMapping("/chat")
//    public ResponseEntity<?> createChat(@RequestBody String message) {
//        return ResponseEntity.ok(assistantService.createChat(null,message));
//    }
//    @PostMapping("/chat-stream")
//    public Flux<Chat> getChatStream(@RequestBody String message) {
//        return assistantService.createChatStream(null,message);
//    }
}
