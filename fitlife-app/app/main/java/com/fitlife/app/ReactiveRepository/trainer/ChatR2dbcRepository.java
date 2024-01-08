package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ChatR2dbcRepository extends R2dbcRepository<Chat, UUID> {
    @Query("select * from Chat c where c.thread_id = :threadId")
    Flux<Chat> findAllByThread(@Param("threadId") UUID threadId);
}
