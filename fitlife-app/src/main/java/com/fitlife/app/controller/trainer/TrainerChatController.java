package com.fitlife.app.controller.trainer;

import com.fitlife.app.dataClass.request.trainer.TrainerChatRequest;
import com.fitlife.app.security.user.CurrentUser;
import com.fitlife.app.security.user.FitLifeUserDetail;
import com.fitlife.app.service.trainer.trainer.TrainerChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/trainer/chat")
@AllArgsConstructor
public class TrainerChatController {
    private final TrainerChatService trainerChatService;
    @PostMapping
    public Mono<ResponseEntity<?>> createChat(@CurrentUser FitLifeUserDetail userDetail, @RequestBody TrainerChatRequest request) {
        return trainerChatService
                .createChat(userDetail.getUser(), request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{threadId}")
    public Mono<ResponseEntity<?>> createChat(@CurrentUser FitLifeUserDetail userDetail, @PathVariable UUID threadId, @RequestBody TrainerChatRequest request) {
        return trainerChatService
                .createChat(userDetail.getUser(), threadId ,request)
                .map(ResponseEntity::ok);
    }
}
