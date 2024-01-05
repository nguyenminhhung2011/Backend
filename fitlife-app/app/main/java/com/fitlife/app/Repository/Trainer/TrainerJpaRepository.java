package com.fitlife.app.Repository.Trainer;

import com.fitlife.app.Model.Trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerJpaRepository extends JpaRepository<Trainer, UUID>{
}
