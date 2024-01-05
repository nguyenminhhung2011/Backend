package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.ChatThread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatThreadR2dbcRepository extends R2dbcRepository<ChatThread, UUID> {

    @Query("select c from chat_thread c where c.trainer.id = :trainerId")
    Flux<ChatThread> findAllByTrainer(@Param("trainerId") UUID trainerId);

    @Query("SELECT c FROM chat_thread c WHERE c.user.id = :userId")
    Flux<ChatThread> findAllByUser(@Param("userId") long userId);
}
