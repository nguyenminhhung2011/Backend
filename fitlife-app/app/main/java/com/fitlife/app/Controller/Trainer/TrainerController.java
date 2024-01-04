package com.fitlife.app.Controller.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Service.Trainer.Assistant.AssistantService;
import com.fitlife.app.Service.Trainer.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final AssistantService assistantService;

//    @PostMapping("/add")
//    public ResponseEntity<Trainer> addTrainer(@RequestBody Trainer trainer) {
//        return ResponseEntity.ok(assistantService.createTrainer(trainer));
//    }
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
