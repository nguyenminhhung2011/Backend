package com.fitlife.app.repository.jpa.trainer;

import com.fitlife.app.model.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerJpaRepository extends JpaRepository<Trainer, UUID>{
}
