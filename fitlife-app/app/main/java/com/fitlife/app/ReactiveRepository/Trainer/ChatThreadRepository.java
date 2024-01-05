package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.ChatThread;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ChatThreadRepository extends ReactiveCrudRepository<ChatThread, UUID> {
//    @Query("SELECT c FROM ChatThread c WHERE c.trainer.id = ?1")
//    Flux<ChatThread> findAllByTrainerId(String trainerId);

    @Query("SELECT c FROM ChatThread c WHERE c.user.id = ?1")
    Flux<ChatThread> findAllByUser(long userId);
}
