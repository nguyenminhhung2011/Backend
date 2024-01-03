package com.fitlife.app.Repository.Trainer;

import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Repository.Generic.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
    @Query("SELECT t FROM Trainer t WHERE t.name LIKE %?1% OR t.id LIKE %?1%")
    List<Trainer> findAllByNameContainsOrIdContains(String id, String name);
}
