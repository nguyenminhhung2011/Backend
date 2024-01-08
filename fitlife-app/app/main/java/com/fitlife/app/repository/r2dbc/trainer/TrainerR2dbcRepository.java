package com.fitlife.app.repository.r2dbc.trainer;

import com.fitlife.app.model.trainer.Trainer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TrainerR2dbcRepository extends R2dbcRepository<Trainer, UUID> {
    @Query("select * from trainer t where t.user_id = :userId")
    Flux<Trainer> findAllByUser(@Param("userId") Long userId);


}
