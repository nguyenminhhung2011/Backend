package com.fitlife.app.repository.Trainer;

import com.fitlife.app.model.Trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerJpaRepository extends JpaRepository<Trainer, UUID>{
}
