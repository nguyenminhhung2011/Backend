package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.ChatThread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
public interface ChatThreadRepository extends R2dbcRepository<ChatThread, String> {
    @Query("SELECT c FROM ChatThread c WHERE c.trainer.id = ?1")
    Flux<ChatThread> findAllByTrainerId(String trainerId);
}
