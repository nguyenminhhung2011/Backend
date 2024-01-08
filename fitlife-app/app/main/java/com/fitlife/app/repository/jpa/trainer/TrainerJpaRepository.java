package com.fitlife.app.repository.jpa.trainer;

import com.fitlife.app.model.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface TrainerJpaRepository extends JpaRepository<Trainer, UUID>{
    @Query("select t from Trainer t where t.user.id = :userId")
    List<Trainer> findAllByUserId(@Param("userId") Long userId);
}
