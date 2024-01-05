package com.fitlife.app.Controller.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadRepository;
import com.fitlife.app.Repository.ThreadChatRepository;
import com.fitlife.app.Repository.User.UserRepository;
import com.fitlife.app.Service.Trainer.Assistant.AssistantService;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.fitlife.app.Service.Trainer.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import retrofit2.http.GET;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final AssistantService assistantService;
    private ChatThreadService chatThreadService;
    UserRepository userRepository;
    ThreadChatRepository threadChatRepository;
    ChatThreadRepository chatThreadRepository;
    @PostMapping("/add")
    public void addTrainer() {
        User user = User.builder()
                .username("hoang")
                .password("123456")
                .build();
        user = userRepository.save(user);
        threadChatRepository.save(ChatThread.builder()
                .title("Testing")
                .user(user)
                .build());
    }

    @GetMapping(value = "/get",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatThread>> get() {
        return chatThreadRepository.findAll()
                .map(chatThread -> ServerSentEvent.<ChatThread>builder()
                        .id(chatThread.getId().toString())
                        .data(chatThread)
                        .build());
    }

    @GetMapping(value = "/get")
    public Flux<ChatThread> getReactive() {
        return chatThreadRepository.findAll();
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
