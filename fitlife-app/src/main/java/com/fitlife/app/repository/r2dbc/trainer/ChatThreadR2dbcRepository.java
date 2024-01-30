package com.fitlife.app.repository.r2dbc.trainer;

import com.fitlife.app.model.trainer.ChatThread;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
    import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatThreadR2dbcRepository extends R2dbcRepository<ChatThread, UUID> {

    @Query("select * from chat_thread c where c.trainer_id = :trainerId")
    Flux<ChatThread> findAllByTrainer(@Param("trainerId") String trainerId);

    @Query("SELECT * FROM chat_thread c WHERE c.user_id = :userId")
    Flux<ChatThread> findAllByUserId(@Param("userId") long userId);
}
