package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ChatRepository extends R2dbcRepository<Chat, String> {

    @Query("select c from Chat c where c.thread.id = ?1")
    Flux<Chat> findAllByThread(String threadId);
}
