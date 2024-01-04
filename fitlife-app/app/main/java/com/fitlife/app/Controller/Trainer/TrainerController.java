package com.fitlife.app.Controller.Trainer;

import com.fitlife.app.DTO.Response.ResponseObject;
import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Service.Trainer.TrainerService;
import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import retrofit2.http.Path;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/add")
    public ResponseEntity<Trainer> addTrainer(@RequestBody Trainer trainer) {
        return ResponseEntity.ok(trainerService.createTrainer(trainer));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrainer( @PathVariable String id) {
        return null;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> createChat(@RequestBody String message) {
        return ResponseEntity.ok(trainerService.createChat(null,message));
    }
    @PostMapping("/chat-stream")
    public Flux<Chat> getChatStream(@RequestBody String message) {
        return trainerService.createChatStream(message);
    }
}
