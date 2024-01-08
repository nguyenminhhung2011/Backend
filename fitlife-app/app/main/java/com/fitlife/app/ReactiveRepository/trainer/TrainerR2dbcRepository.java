package com.fitlife.app.ReactiveRepository.trainer;

import com.fitlife.app.model.Trainer.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TrainerR2dbcRepository extends R2dbcRepository<Trainer, UUID> {
    @Query("select t from Trainer t where t.user.id = ?1")
    Flux<Trainer> findAllByUser(Long id);


}
