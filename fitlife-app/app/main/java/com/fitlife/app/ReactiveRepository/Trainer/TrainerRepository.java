package com.fitlife.app.ReactiveRepository.Trainer;

import com.fitlife.app.Model.Trainer.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TrainerRepository extends R2dbcRepository<Trainer, String> {
    @Query("SELECT t FROM Trainer t WHERE t.name LIKE %?1% OR t.id LIKE %?1%")
    Flux<Trainer> findAllByNameContainsOrIdContains(String id, String name);
}
