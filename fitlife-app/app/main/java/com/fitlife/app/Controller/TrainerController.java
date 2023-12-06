package com.fitlife.app.Controller;

import com.fitlife.app.Service.Trainer.TrainerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
@AllArgsConstructor
public class TrainerController {
    private final TrainerServiceImpl trainerService;
    record Message(String message){}
    @PostMapping("/chat")
    ResponseEntity<?> chat(@RequestBody Message message ){
        return ResponseEntity.ok(trainerService.chat(message.message));
    }
}
