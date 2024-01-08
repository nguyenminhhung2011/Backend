package com.fitlife.app.controller.trainer;

import com.fitlife.app.dataClass.dto.trainer.TrainerDetailDto;
import com.fitlife.app.dataClass.dto.trainer.TrainerDto;
import com.fitlife.app.security.model.CurrentUser;
import com.fitlife.app.security.model.FitLifeUserDetail;
import com.fitlife.app.service.trainer.trainer.TrainerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/trainer")
@AllArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    @GetMapping
    public Flux<TrainerDto> getAllTrainer(@CurrentUser FitLifeUserDetail userDetail) {
        return trainerService.getTrainerByUser(userDetail.getId());
    }

    @PostMapping
    public ResponseEntity<?> createTrainer(@CurrentUser FitLifeUserDetail userDetail,@RequestBody TrainerDto trainerDto) {
        return ResponseEntity.ok(trainerService.createTrainer(userDetail.getId(), trainerDto));
    }

    @DeleteMapping("/{trainerId}")
    public void deleteTrainer(@CurrentUser FitLifeUserDetail userDetail, @PathVariable UUID trainerId) {
        trainerService.deleteTrainer(userDetail.getId(), trainerId);
    }

    @PutMapping("/{trainerId}")
    public ResponseEntity<?> updateTrainer(@CurrentUser FitLifeUserDetail userDetail, @PathVariable UUID trainerId, TrainerDto trainerDto) {
        return ResponseEntity.ok(trainerService.updateTrainer(userDetail.getId(), trainerId, trainerDto));
    }

    @GetMapping("/{trainerId}")
    public ResponseEntity<Mono<TrainerDetailDto>> getTrainerById(@PathVariable UUID trainerId) {
        return ResponseEntity.ok(trainerService.getById(trainerId));
    }
}
