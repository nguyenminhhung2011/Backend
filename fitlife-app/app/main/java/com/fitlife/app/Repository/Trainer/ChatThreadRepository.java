package com.fitlife.app.Repository.Trainer;

import com.fitlife.app.Model.Trainer.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatThreadRepository extends JpaRepository<ChatThread, String> {
    @Query("SELECT c FROM ChatThread c WHERE c.trainer.id = ?1")
    List<ChatThread> findAllByTrainerId(String trainerId);
}
