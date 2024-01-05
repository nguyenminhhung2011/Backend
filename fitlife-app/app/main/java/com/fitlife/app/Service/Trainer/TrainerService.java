package com.fitlife.app.Service.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.ReactiveRepository.Trainer.TrainerR2dbcRepository;
import com.fitlife.app.Repository.Trainer.ChatThreadJpaRepository;
import com.fitlife.app.Repository.Trainer.TrainerJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainerService {
    private final TrainerJpaRepository trainerJpaRepository;
    private final TrainerR2dbcRepository trainerR2dbcRepository;

    public Mono<Long> count() {
        return trainerR2dbcRepository.count();
    }

    public Flux<Trainer> getTrainerByUser(Long userId) {
        return trainerR2dbcRepository.findAllByUser(userId);
    }

    public Mono<Trainer> getById(UUID trainerId) {
        return trainerR2dbcRepository.findById(trainerId);
    }

    public Flux<ChatThread> getALlChatThread(UUID trainerId){
        return trainerR2dbcRepository.findById(trainerId)
                .flatMapIterable(Trainer::getThreads);
    }

    public Trainer createTrainer(Trainer trainer) {
        return trainerJpaRepository.saveAndFlush(trainer);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerJpaRepository.save(trainer);
    }

    public void deleteTrainer(UUID trainerId) {
        trainerJpaRepository.deleteById(trainerId);
    }


}
