package com.fitlife.app.Controller;

import com.fitlife.app.Service.Trainer.TrainerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
     private final TrainerService trainerService;

     public TrainerController(TrainerService trainerService) {
         this.trainerService = trainerService;
     }

     @PostMapping("/chat")
     public String chat(@RequestBody String  message){
         return trainerService.chat(message);
     }
}
